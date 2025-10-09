#!/bin/bash

# Devin API Helper Script
# Usage: ./devin-api.sh <action> [parameters]

set -e

DEVIN_API_BASE="https://api.devin.ai/v1"
DEVIN_API_KEY="${DEVIN_API_KEY:-}"

if [ -z "$DEVIN_API_KEY" ]; then
    echo "❌ Error: DEVIN_API_KEY environment variable is required"
    exit 1
fi

# Function to create a Devin session
create_session() {
    local prompt="$1"
    local title="$2"
    local tags="$3"
    
    echo "🚀 Creating Devin session..."
    
    local payload=$(jq -n \
        --arg prompt "$prompt" \
        --arg title "$title" \
        --argjson tags "$tags" \
        '{
            prompt: $prompt,
            title: $title,
            tags: $tags,
            idempotent: true
        }')
    
    local response=$(curl -s --fail \
        --request POST \
        --url "$DEVIN_API_BASE/sessions" \
        --header "Authorization: Bearer $DEVIN_API_KEY" \
        --header "Content-Type: application/json" \
        --data "$payload")
    
    echo "$response"
}

# Function to send a message to an existing session
send_message() {
    local session_id="$1"
    local message="$2"
    
    echo "💬 Sending message to session $session_id..."
    
    local payload=$(jq -n \
        --arg message "$message" \
        '{
            message: $message
        }')
    
    local response=$(curl -s --fail \
        --request POST \
        --url "$DEVIN_API_BASE/sessions/$session_id/messages" \
        --header "Authorization: Bearer $DEVIN_API_KEY" \
        --header "Content-Type: application/json" \
        --data "$payload")
    
    echo "$response"
}

# Function to get session status
get_session() {
    local session_id="$1"
    
    echo "📊 Getting session status for $session_id..."
    
    local response=$(curl -s --fail \
        --request GET \
        --url "$DEVIN_API_BASE/sessions/$session_id" \
        --header "Authorization: Bearer $DEVIN_API_KEY")
    
    echo "$response"
}

# Function to list all sessions
list_sessions() {
    echo "📋 Listing all sessions..."
    
    local response=$(curl -s --fail \
        --request GET \
        --url "$DEVIN_API_BASE/sessions" \
        --header "Authorization: Bearer $DEVIN_API_KEY")
    
    echo "$response"
}

# Function to create a build fix session with comprehensive context
create_build_fix_session() {
    local repo_url="$1"
    local branch="$2"
    local commit_sha="$3"
    local build_run_url="$4"
    local build_logs="$5"
    
    local prompt="Fix the failing CI/CD build in this Spring Boot project.

🔧 **Build Context:**
- Repository: $repo_url
- Branch: $branch
- Failed Commit: $commit_sha
- Build Run: $build_run_url

📋 **Build Failure Details:**
$build_logs

🎯 **Your Mission:**
1. Clone the repository: git clone $repo_url && cd \$(basename $repo_url .git)
2. Checkout the failing branch: git checkout $branch
3. Analyze the build failure by running: ./gradlew spotlessCheck
4. If formatting fails, run: ./gradlew spotlessApply
5. Check for compilation errors: ./gradlew compileJava compileTestJava
6. Run tests: ./gradlew test
7. Fix any issues found (imports, syntax, type mismatches, test failures)
8. Verify the complete build: ./gradlew clean build
9. Commit your fixes: git add . && git commit -m \"fix: resolve CI build failures\"
10. Push the fixes: git push origin $branch

⚡ **Focus Areas:**
- Java compilation errors (missing imports, syntax errors)
- Code formatting violations (Google Java Format)
- Unit test failures
- Gradle build configuration issues

Make targeted, minimal fixes while preserving intended functionality. The goal is to make the CI build pass."

    local title="Auto-fix CI build failure - $branch"
    local tags='["auto-fix", "ci-failure", "spring-boot", "gradle"]'
    
    create_session "$prompt" "$title" "$tags"
}

# Main script logic
case "$1" in
    "create")
        create_session "$2" "$3" "$4"
        ;;
    "send")
        send_message "$2" "$3"
        ;;
    "get")
        get_session "$2"
        ;;
    "list")
        list_sessions
        ;;
    "build-fix")
        create_build_fix_session "$2" "$3" "$4" "$5" "$6"
        ;;
    *)
        echo "Usage: $0 {create|send|get|list|build-fix} [parameters]"
        echo ""
        echo "Commands:"
        echo "  create <prompt> <title> <tags_json>  - Create a new session"
        echo "  send <session_id> <message>          - Send message to session"
        echo "  get <session_id>                     - Get session details"
        echo "  list                                 - List all sessions"
        echo "  build-fix <repo> <branch> <commit> <build_url> <logs> - Create build fix session"
        exit 1
        ;;
esac
