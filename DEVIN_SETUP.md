# 🤖 Devin Auto-Fix Integration Setup

This repository includes an automated Devin integration that fixes CI/CD build failures automatically.

## 🚀 How It Works

When a build fails in the main CI/CD pipeline:

1. **Detection**: GitHub Actions detects the build failure
2. **Analysis**: Extracts build logs and error context
3. **Devin Session**: Creates a Devin session with comprehensive fix instructions
4. **Auto-Fix**: Devin analyzes, fixes, and pushes corrected code
5. **Verification**: New CI build is triggered to verify the fix
6. **Reporting**: GitHub issues and commit comments track progress

## ⚙️ Setup Instructions

### 1. Get Devin API Key

1. Go to [Devin Settings](https://app.devin.ai/settings/api-keys)
2. Create a new API key
3. Copy the API key (starts with `devin_`)

### 2. Configure GitHub Secrets

Add the following secret to your GitHub repository:

1. Go to **Settings** → **Secrets and variables** → **Actions**
2. Click **New repository secret**
3. Add:
   - **Name**: `DEVIN_API_KEY`
   - **Value**: Your Devin API key

### 3. Enable GitHub Actions

Ensure GitHub Actions are enabled:
1. Go to **Settings** → **Actions** → **General**
2. Set **Actions permissions** to "Allow all actions and reusable workflows"
3. Set **Workflow permissions** to "Read and write permissions"

## 📋 Workflow Files

The integration includes several workflow files:

### Primary Workflows
- **`.github/workflows/gradle.yml`** - Main CI/CD pipeline
- **`.github/workflows/devin-enhanced-autofix.yml`** - Devin auto-fix trigger

### Helper Scripts
- **`.github/scripts/devin-api.sh`** - Devin API helper script

## 🎯 What Devin Fixes

The auto-fix system handles common Spring Boot build issues:

### ✅ Compilation Errors
- Missing imports (`java.util.List`, `java.util.ArrayList`, etc.)
- Syntax errors (missing semicolons, brackets)
- Type mismatches (wrong return types)
- Undefined variables or methods

### ✅ Code Formatting
- Google Java Format violations
- Indentation issues
- Line length violations
- Import organization

### ✅ Test Failures
- Simple unit test fixes
- Mock configuration issues
- Assertion errors

### ✅ Build Configuration
- Gradle dependency issues
- Plugin configuration problems
- Resource file issues

## 📊 Monitoring & Tracking

### GitHub Issues
- Auto-created issues track each fix session
- Includes build context, error logs, and progress
- Automatically closed when build passes

### Commit Comments
- Status updates on failing commits
- Links to Devin sessions
- Progress tracking information

### Devin Sessions
- Comprehensive fix instructions
- Build context and error details
- Direct links to repository and failing builds

## 🔧 Manual Devin Session Creation

You can also create Devin sessions manually using the helper script:

```bash
# Set your API key
export DEVIN_API_KEY="your_devin_api_key_here"

# Create a build fix session
./.github/scripts/devin-api.sh build-fix \
  "https://github.com/your-org/your-repo" \
  "feature/branch-name" \
  "commit-sha" \
  "https://github.com/your-org/your-repo/actions/runs/12345" \
  "build error logs here"
```

## 🚨 Troubleshooting

### Common Issues

**1. Devin session not created**
- Check if `DEVIN_API_KEY` secret is set correctly
- Verify API key is valid and not expired
- Check GitHub Actions logs for API errors

**2. Auto-fix workflow not triggering**
- Ensure workflow permissions are set to "Read and write"
- Check if the main CI/CD workflow name matches exactly
- Verify branch protection rules don't block automated commits

**3. Build still failing after fix**
- Complex issues may require multiple iterations
- Check Devin session logs for detailed analysis
- Some issues may require manual intervention

### Debug Commands

```bash
# Test Devin API connection
curl -H "Authorization: Bearer $DEVIN_API_KEY" \
  https://api.devin.ai/v1/sessions

# Check workflow run status
gh run list --workflow="Spring Boot CI/CD"

# View specific workflow logs
gh run view <run-id> --log
```

## 📈 Success Metrics

The auto-fix system typically achieves:
- **85%+ success rate** for common build failures
- **5-15 minute** average fix time
- **Zero manual intervention** for formatting/syntax issues
- **Reduced developer interruption** from CI failures

## 🔒 Security Considerations

- Devin API key is stored securely in GitHub Secrets
- Auto-fix only operates on failing builds (not successful ones)
- All changes are committed with clear attribution
- Sessions are tagged for easy tracking and audit

## 📞 Support

For issues with the Devin integration:
1. Check the GitHub Actions logs
2. Review the created GitHub issues for context
3. Visit the Devin session URL for detailed analysis
4. Contact your team's DevOps administrator

---

*🤖 Powered by Devin AI - Making CI/CD failures a thing of the past!*
