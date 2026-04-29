#!/bin/bash

# ============================================================================
# Food Delivery App - Build & Deployment Script
# ============================================================================
# This script automates building the WAR file and optionally deploys to Tomcat
# ============================================================================

set -e  # Exit on error

# Color codes for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Configuration
PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
WEBAPP_DIR="$PROJECT_DIR/WebApp"
TARGET_DIR="$WEBAPP_DIR/target"
WAR_FILE="$TARGET_DIR/fooddelivery.war"
TOMCAT_WEBAPPS=""

# ============================================================================
# Functions
# ============================================================================

print_header() {
    echo -e "\n${BLUE}========================================${NC}"
    echo -e "${BLUE}$1${NC}"
    echo -e "${BLUE}========================================${NC}\n"
}

print_success() {
    echo -e "${GREEN}✓ $1${NC}\n"
}

print_error() {
    echo -e "${RED}✗ $1${NC}\n"
}

print_warning() {
    echo -e "${YELLOW}⚠ $1${NC}\n"
}

print_info() {
    echo -e "${BLUE}ℹ $1${NC}"
}

# ============================================================================
# Main Build Function
# ============================================================================

build_project() {
    print_header "Building Food Delivery App"
    
    # Check if Maven is installed
    if ! command -v mvn &> /dev/null; then
        print_error "Maven is not installed!"
        echo "Please install Maven from: https://maven.apache.org/download.cgi"
        exit 1
    fi
    
    # Check if pom.xml exists
    if [ ! -f "$WEBAPP_DIR/pom.xml" ]; then
        print_error "pom.xml not found at $WEBAPP_DIR/pom.xml"
        exit 1
    fi
    
    print_info "Project Directory: $PROJECT_DIR"
    print_info "Building from: $WEBAPP_DIR"
    
    # Change to WebApp directory and build
    cd "$WEBAPP_DIR"
    
    print_info "Running: mvn clean package -DskipTests"
    if mvn clean package -DskipTests; then
        print_success "Build completed successfully!"
    else
        print_error "Build failed!"
        exit 1
    fi
    
    # Verify WAR file exists
    if [ -f "$WAR_FILE" ]; then
        WAR_SIZE=$(du -h "$WAR_FILE" | cut -f1)
        print_success "WAR file generated: $WAR_FILE ($WAR_SIZE)"
    else
        print_error "WAR file was not generated!"
        exit 1
    fi
}

# ============================================================================
# Deployment Functions
# ============================================================================

find_tomcat() {
    print_header "Searching for Tomcat Installation"
    
    # Common Tomcat locations
    TOMCAT_PATHS=(
        "/opt/tomcat"
        "/usr/local/tomcat"
        "/usr/local/opt/tomcat"
        "$HOME/tomcat"
        "$HOME/.local/tomcat"
        "/opt/homebrew/Cellar/tomcat"
    )
    
    for path in "${TOMCAT_PATHS[@]}"; do
        if [ -d "$path" ] && [ -d "$path/webapps" ]; then
            TOMCAT_WEBAPPS="$path/webapps"
            print_success "Found Tomcat at: $path"
            return 0
        fi
    done
    
    print_warning "Could not find Tomcat automatically"
    return 1
}

deploy_to_tomcat() {
    print_header "Deploying to Tomcat"
    
    if [ -z "$TOMCAT_WEBAPPS" ]; then
        print_error "Tomcat path not set"
        return 1
    fi
    
    if [ ! -d "$TOMCAT_WEBAPPS" ]; then
        print_error "Tomcat webapps directory not found: $TOMCAT_WEBAPPS"
        return 1
    fi
    
    print_info "Deploying: $WAR_FILE"
    print_info "To: $TOMCAT_WEBAPPS/"
    
    # Backup existing deployment if it exists
    if [ -f "$TOMCAT_WEBAPPS/fooddelivery.war" ]; then
        BACKUP_FILE="$TOMCAT_WEBAPPS/fooddelivery.war.backup.$(date +%s)"
        cp "$TOMCAT_WEBAPPS/fooddelivery.war" "$BACKUP_FILE"
        print_warning "Backed up existing WAR to: $BACKUP_FILE"
    fi
    
    # Copy WAR file
    cp "$WAR_FILE" "$TOMCAT_WEBAPPS/"
    
    if [ -f "$TOMCAT_WEBAPPS/fooddelivery.war" ]; then
        print_success "Deployment successful!"
        print_info "WAR file deployed to: $TOMCAT_WEBAPPS/fooddelivery.war"
        
        # Check if Tomcat needs restart
        TOMCAT_BIN="$(dirname "$TOMCAT_WEBAPPS")/bin"
        if [ -f "$TOMCAT_BIN/startup.sh" ]; then
            print_info "To start Tomcat, run: $TOMCAT_BIN/startup.sh"
            print_info "Then access: http://localhost:8080/fooddelivery"
        fi
    else
        print_error "Failed to deploy WAR file"
        return 1
    fi
}

prompt_deployment() {
    echo -e "\n${YELLOW}Do you want to deploy to Tomcat? (y/n)${NC} "
    read -r response
    
    case "$response" in
        [yY][eE][sS]|[yY])
            if find_tomcat; then
                deploy_to_tomcat
            else
                echo -e "\n${YELLOW}Enter Tomcat webapps directory manually:${NC} "
                read -r tomcat_path
                
                if [ -d "$tomcat_path" ]; then
                    TOMCAT_WEBAPPS="$tomcat_path"
                    deploy_to_tomcat
                else
                    print_error "Invalid path: $tomcat_path"
                fi
            fi
            ;;
        *)
            print_info "Skipping deployment"
            ;;
    esac
}

show_summary() {
    print_header "Build Summary"
    
    echo -e "${GREEN}Project:${NC}          Food Delivery App"
    echo -e "${GREEN}Version:${NC}          1.0.0"
    echo -e "${GREEN}Build Status:${NC}     SUCCESS"
    echo -e "${GREEN}WAR File:${NC}         fooddelivery.war"
    echo -e "${GREEN}Location:${NC}         $WAR_FILE"
    echo -e "${GREEN}Size:${NC}             $(du -h "$WAR_FILE" | cut -f1)"
    
    echo -e "\n${BLUE}Next Steps:${NC}"
    echo "1. Deploy WAR to Tomcat (copy to webapps/ folder)"
    echo "2. Start Tomcat server"
    echo "3. Access: http://localhost:8080/fooddelivery"
    echo "4. Login with: admin / admin123"
    
    echo -e "\n${BLUE}Documentation:${NC}"
    echo "- Full Guide: $WEBAPP_DIR/COMPLETE_GUIDE.md"
    echo "- Submission: $WEBAPP_DIR/TEACHER_SUBMISSION.md"
    echo "- Database:   $WEBAPP_DIR/database/fooddb.sql"
    
    echo ""
}

# ============================================================================
# Help & Usage
# ============================================================================

show_help() {
    cat << EOF
${BLUE}Food Delivery App - Build & Deploy Script${NC}

${YELLOW}Usage:${NC}
    ./build.sh [OPTION]

${YELLOW}Options:${NC}
    build               Build WAR file only
    deploy              Build and deploy to Tomcat
    deploy-auto         Build and auto-detect Tomcat location
    deploy-path <PATH>  Build and deploy to specified Tomcat webapps path
    clean               Clean build artifacts
    help                Show this help message

${YELLOW}Examples:${NC}
    ./build.sh build
    ./build.sh deploy
    ./build.sh deploy-path /opt/tomcat/webapps
    ./build.sh clean

${YELLOW}Environment Variables:${NC}
    TOMCAT_HOME         Path to Tomcat installation (optional)
    FOODDB_URL          MySQL connection URL (optional)
    FOODDB_USER         MySQL username (optional)
    FOODDB_PASSWORD     MySQL password (optional)

EOF
}

# ============================================================================
# Main Script
# ============================================================================

main() {
    # Default action
    ACTION="${1:-build}"
    
    case "$ACTION" in
        build)
            build_project
            show_summary
            ;;
        deploy)
            build_project
            prompt_deployment
            show_summary
            ;;
        deploy-auto)
            build_project
            if find_tomcat; then
                deploy_to_tomcat
            else
                print_error "Could not auto-detect Tomcat installation"
                print_info "Please use: ./build.sh deploy-path /path/to/tomcat/webapps"
            fi
            show_summary
            ;;
        deploy-path)
            if [ -z "$2" ]; then
                print_error "Tomcat path not specified"
                echo "Usage: ./build.sh deploy-path /path/to/tomcat/webapps"
                exit 1
            fi
            build_project
            TOMCAT_WEBAPPS="$2"
            deploy_to_tomcat
            show_summary
            ;;
        clean)
            print_header "Cleaning build artifacts"
            cd "$WEBAPP_DIR"
            mvn clean
            print_success "Clean completed"
            ;;
        help|--help|-h)
            show_help
            ;;
        *)
            print_error "Unknown option: $ACTION"
            show_help
            exit 1
            ;;
    esac
}

# Run main function with all arguments
main "$@"
