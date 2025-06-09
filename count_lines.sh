#!/usr/bin/env bash
#
# count-lines-by-ext.sh
# Usage: ./count-lines-by-ext.sh [rootDir]
#   rootDir: directory to scan (defaults to current dir)

set -euo pipefail

ROOT="${1:-.}"

# Ensure we're running with bash ≥ 4 for associative arrays
if (( BASH_VERSINFO[0] < 4 )); then
  echo "Error: this script requires bash version ≥ 4." >&2
  exit 1
fi

# Declare associative arrays for stats
declare -A file_count lines_count

# Walk files, skipping node_modules
while IFS= read -r -d '' file; do
  # Determine extension (or NOEXT)
  if [[ "$file" == *.* ]]; then
    ext="${file##*.}"
  else
    ext="NOEXT"
  fi

  # Count lines in the file
  this_lines=$(wc -l < "$file")

  # Safely increment counts, using default 0 for unset keys
  file_count["$ext"]=$(( ${file_count["$ext"]:-0} + 1 ))
  lines_count["$ext"]=$(( ${lines_count["$ext"]:-0} + this_lines ))
done < <(
  find "$ROOT" \
    -type d -name node_modules -prune -o \
    -type f -print0
)

# Print header
printf "%-10s %10s %10s\n" "EXT" "FILES" "LINES"
printf "%-10s %10s %10s\n" "----------" "----------" "----------"

# Sort and display by descending line count
for ext in "${!lines_count[@]}"; do
  printf "%s\t%s\t%s\n" \
    "${lines_count[$ext]}" \
    "${file_count[$ext]}" \
    "$ext"
done | sort -rn \
  | while IFS=$'\t' read -r tot_lines tot_files ext; do
      printf "%-10s %10d %10d\n" "$ext" "$tot_files" "$tot_lines"
    done

