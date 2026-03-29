#!/bin/sh

set -eu

repo_root="$(cd "$(dirname "$0")/.." && pwd)"

git -C "$repo_root" config core.hooksPath .githooks
git -C "$repo_root" config commit.template .gitmessage

echo "Configured git hooks path to .githooks"
echo "Configured commit template to .gitmessage"
echo "Commit message format:"
echo "type: subject"
echo
echo "  1. change summary"
echo "  2. change summary"
