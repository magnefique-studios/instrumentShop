#!/usr/bin/env bash
set -euo pipefail

# -------------------------------------------------------------------
# run_code_analysis.sh
# Runs an AWS Transform Custom transformation in headless mode.
# Retries up to MAX_RETRIES times on failure.
#
# Usage:
#   ./run_code_analysis.sh [-n <name>] [-p <path>] [-c <build-cmd>]
#                          [-N <pr-name>] [-T <pr-text>]
#                          [-A <additional-context>] [-D <solution-diff>]
#
# Defaults:
#   -n  AWS/comprehensive-codebase-analysis
#   -p  .                   (current directory)
#   -c  mvn clean install   (Maven build)
#   -N  (empty)             PR name
#   -T  (empty)             PR text/description
#   -A  (empty)             Additional plan context
#   -D  (empty)             Solution diff (git diff --stat)
# -------------------------------------------------------------------

TRANSFORMATION_NAME="AWS/comprehensive-codebase-analysis"
CODE_PATH="."
BUILD_CMD="mvn clean install"
PR_NAME=""
PR_TEXT=""
ADDITIONAL_CONTEXT=""
SOLUTION_DIFF=""
MAX_RETRIES=3

while getopts "n:p:c:N:T:A:D:" opt; do
  case $opt in
    n) TRANSFORMATION_NAME="$OPTARG" ;;
    p) CODE_PATH="$OPTARG" ;;
    c) BUILD_CMD="$OPTARG" ;;
    N) PR_NAME="$OPTARG" ;;
    T) PR_TEXT="$OPTARG" ;;
    A) ADDITIONAL_CONTEXT="$OPTARG" ;;
    D) SOLUTION_DIFF="$OPTARG" ;;
    *) echo "Usage: $0 [-n <name>] [-p <path>] [-c <build-cmd>] [-N <pr-name>] [-T <pr-text>] [-A <context>] [-D <diff>]" && exit 1 ;;
  esac
done

# Write config to a temp JSON file to avoid shell escaping issues with -g
CONFIG_FILE=$(mktemp /tmp/atx_config_XXXXXX.json)
trap "rm -f $CONFIG_FILE" EXIT

python3 -c "
import json, sys
ctx = 'Please limit the scope of documentation to this PR and related changes only!'
ctx += ' PR_NAME: ' + sys.argv[1]
ctx += ', PR_TEXT: ' + sys.argv[2]
ctx += ', Solution_Diff: ' + sys.argv[3]
if sys.argv[4]:
    ctx += ' ' + sys.argv[4]
with open(sys.argv[5], 'w') as f:
    json.dump({'additionalPlanContext': ctx}, f)
" "$PR_NAME" "$PR_TEXT" "$SOLUTION_DIFF" "$ADDITIONAL_CONTEXT" "$CONFIG_FILE"

echo "=== AWS Transform Custom ==="
echo "Transformation: $TRANSFORMATION_NAME"
echo "Code path:      $CODE_PATH"
echo "Build command:  $BUILD_CMD"
echo "PR Name:        $PR_NAME"
echo "PR Text:        $PR_TEXT"
echo "Config file:    $CONFIG_FILE"
cat "$CONFIG_FILE"
echo ""
echo "============================"

attempt=1
while [ $attempt -le $MAX_RETRIES ]; do
  echo "--- Attempt $attempt of $MAX_RETRIES ---"

  if atx custom def exec \
    -n "$TRANSFORMATION_NAME" \
    -p "$CODE_PATH" \
    -c "$BUILD_CMD" \
    -g "file://${CONFIG_FILE}" \
    -x -t; then
    echo "=== Transformation completed successfully ==="
    exit 0
  fi

  echo "Attempt $attempt failed."
  attempt=$((attempt + 1))

  if [ $attempt -le $MAX_RETRIES ]; then
    echo "Retrying in 10 seconds..."
    sleep 10
  fi
done

echo "=== All $MAX_RETRIES attempts failed ==="
exit 1
