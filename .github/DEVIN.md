# Devin CI Auto-Fix

Automatic CI failure fixing powered by Devin AI.

## How it works

When a CI build fails on a **feature branch**, Devin automatically:
1. Receives the failing branch name
2. Clones the repo and checks out that branch
3. Runs the build to see what failed
4. Fixes the issue
5. Pushes the corrected code

## Requirements

- `DEVIN_API_KEY` secret configured in repository settings
- Get your API key from https://app.devin.ai/settings/api-keys

## Workflow

- `.github/workflows/devin-ci-fix.yml` - Triggers Devin on feature branch failures
- Only runs on feature branches (not master or devin/* branches)
