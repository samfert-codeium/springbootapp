# Devin CI Auto-Fix

This repository has automatic CI failure fixing powered by Devin AI.

## How it works

When a CI build fails, Devin automatically:
1. Receives notification with the failing branch
2. Clones the repo and checks out the branch
3. Runs the build to diagnose the issue
4. Fixes the problem
5. Pushes the corrected code back

## Setup

The integration requires one secret:
- `DEVIN_API_KEY` - Your Devin API key from https://app.devin.ai/settings/api-keys

## Files

- `.github/workflows/devin-ci-fix.yml` - Triggers Devin when builds fail
- `.github/workflows/gradle.yml` - Main CI build workflow
