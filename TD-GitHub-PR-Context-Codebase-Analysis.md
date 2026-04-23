# GitHub-PR-Context-Codebase-Analysis

## Objective

Extract a GitHub Pull Request URL from the additionalContext field, retrieve the PR metadata (name, body, diff) via the GitHub API, clone the repository and check out the PR branch locally, build and test the branch to ensure it is in a good state, execute the AWS/comprehensive-codebase-analysis transformation definition against the checked-out PR branch using the PR data to scope the documentation changes, and then push those documentation and analysis changes back to the PR branch as a new commit.

## Summary

This transformation parses the additionalContext field to find a GitHub Pull Request URL and uses the GitHub REST API to fetch the PR metadata (name, body, and diff). It then clones the repository and checks out the PR's source branch locally. The checked-out branch is built and its tests are run to confirm the code is in a good state before proceeding. The retrieved PR context (name, body, and diff) is assembled into a structured prompt that scopes what the documentation changes should focus on, and the AWS/comprehensive-codebase-analysis transformation definition is executed against the locally checked-out PR branch. After the analysis completes, the transformation parses the output to identify suggested documentation and analysis file changes, applies those changes to the local working copy, and pushes them as a new commit to the PR's source branch via the GitHub API.

## Entry Criteria

1. The additionalContext field contains a valid GitHub Pull Request URL in the format: https://github.com/{owner}/{repo}/pull/{pull_number}
2. The GitHub repository referenced in the URL is accessible (public, or accessible via a configured GitHub personal access token)
3. The configured GitHub token has write permissions (contents:write) to the repository so that commits can be pushed to the PR branch
4. The Pull Request exists and is in an open state (changes cannot be pushed to closed or merged PRs)
5. Git is installed and available on the local system for cloning and checking out the repository
6. The build tool required by the repository (e.g., Maven, Gradle, npm) is installed and available on the local system
7. The AWS/comprehensive-codebase-analysis transformation definition is available and retrievable from the transformation registry

## Implementation Steps

1. Parse the additionalContext field and extract the GitHub Pull Request URL using a regex pattern matching https://github.com/{owner}/{repo}/pull/{pull_number}
2. From the extracted URL, isolate the owner, repo, and pull_number path components
3. Call the GitHub REST API endpoint GET /repos/{owner}/{repo}/pulls/{pull_number} to retrieve the PR metadata, specifically the name (title), body, and head fields from the JSON response. From the head field, extract the ref (branch name) and sha (latest commit SHA) of the PR's source branch
4. Call the GitHub REST API endpoint GET /repos/{owner}/{repo}/pulls/{pull_number} with the Accept header set to application/vnd.github.v3.diff to retrieve the full diff content of the Pull Request
5. Clone the repository locally by running git clone https://github.com/{owner}/{repo}.git into a temporary working directory. Then check out the PR's source branch by running git checkout {branch_name} within the cloned repository
6. Build the checked-out branch code using the repository's build tool (e.g., mvn clean install, gradle build, npm install && npm test). Verify that the build completes successfully and all tests pass. If the build or tests fail, halt the transformation and report the failure details
7. Assemble the retrieved PR data into a structured context block with the following format:
   - PR Name: {title}
   - PR Description: {body}
   - PR Diff: {diff content}
   This context block is used to scope the documentation and analysis changes so they focus specifically on the areas affected by the PR
8. Retrieve the AWS/comprehensive-codebase-analysis transformation definition from the transformation registry
9. Execute the AWS/comprehensive-codebase-analysis transformation definition against the locally checked-out PR branch directory, passing the assembled PR context (name, body, and diff) as additional input so the analysis and documentation changes are scoped to the PR's intent and affected areas
10. Parse the analysis output to identify all suggested documentation and file changes. For each suggested change, extract the target file path and the updated file content
11. For each file with suggested changes, retrieve the current file content from the PR branch by calling GET /repos/{owner}/{repo}/contents/{path}?ref={branch_name} to get the current blob SHA for that file
12. For each changed file, create a new blob by calling POST /repos/{owner}/{repo}/git/blobs with the updated file content (base64-encoded) and encoding set to base64
13. Create a new tree by calling POST /repos/{owner}/{repo}/git/trees with the base_tree set to the latest commit's tree SHA and the tree array containing entries for each changed file, referencing the new blob SHAs created in the previous step
14. Create a new commit by calling POST /repos/{owner}/{repo}/git/commits with the new tree SHA, the parent set to the latest commit SHA on the PR branch, and a commit message such as "Apply documentation updates from comprehensive codebase analysis"
15. Update the PR branch reference by calling PATCH /repos/{owner}/{repo}/git/refs/heads/{branch_name} with the new commit SHA to push the changes to the PR branch
16. Clean up the temporary local clone directory
17. Return a summary of the operation including the list of files modified, the new commit SHA, and a link to the updated Pull Request

## Validation / Exit Criteria

1. The GitHub Pull Request URL was successfully parsed from the additionalContext field, and valid owner, repo, and pull_number values were extracted
2. The GitHub API calls returned successful responses (HTTP 200) for both the PR metadata and the PR diff endpoints
3. The PR is confirmed to be in an open state and the source branch ref and SHA were successfully extracted
4. The PR name, body, and diff data were all retrieved and are non-empty (body may be empty if the PR author left it blank, which is acceptable)
5. The repository was successfully cloned and the PR's source branch was checked out locally
6. The build completed successfully and all tests passed on the checked-out PR branch
7. The assembled context block contains all three sections (name, description, diff) in the expected format and is used to scope the analysis
8. The AWS/comprehensive-codebase-analysis transformation definition was successfully retrieved from the registry
9. The AWS/comprehensive-codebase-analysis transformation executed successfully against the locally checked-out PR branch with the PR context provided to scope the documentation changes
10. The analysis output was parsed and at least one documentation or file change suggestion was identified
11. New blobs were created successfully for each changed file (HTTP 201 responses from the blobs API)
12. A new tree was created successfully referencing all the updated blobs
13. A new commit was created successfully with the correct parent and tree references
14. The PR branch reference was updated to point to the new commit (HTTP 200 from the refs API)
15. The temporary local clone directory was cleaned up
16. The final output includes the list of modified files, the new commit SHA, and the PR URL confirming the changes were pushed
