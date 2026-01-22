#!/usr/bin/env bash
set -euo pipefail

# ------------------------------------------------------------
# One-click project scaffold generator
# Location: /home/zzn16/Full_stack_study/scripts
# Target  : /home/zzn16/Full_stack_study/personal-portal
# ------------------------------------------------------------

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(cd "${SCRIPT_DIR}/.." && pwd)"
TARGET_DIR="${ROOT_DIR}/personal-portal"

mkdir -p "${TARGET_DIR}"

# Helper: mkdir -p for a list of dirs under TARGET_DIR
mkd() {
  for d in "$@"; do
    mkdir -p "${TARGET_DIR}/${d}"
  done
}

# Helper: create file if not exists, ensure parent dir exists
mkf() {
  local f="$1"
  mkdir -p "$(dirname "${TARGET_DIR}/${f}")"
  if [[ ! -f "${TARGET_DIR}/${f}" ]]; then
    : > "${TARGET_DIR}/${f}"
  fi
}

# -------------------------
# Top-level folders
# -------------------------
mkd \
  "docs" \
  "scripts" \
  "portal-frontend" \
  "portal-backend" \
  "ai-services"

# -------------------------
# personal-portal/scripts
# -------------------------
mkf "scripts/init_db.sh"
mkf "scripts/dev.sh"
mkf "scripts/build.sh"

# -------------------------
# portal-frontend
# -------------------------
mkd \
  "portal-frontend/src/api" \
  "portal-frontend/src/assets" \
  "portal-frontend/src/components" \
  "portal-frontend/src/layouts" \
  "portal-frontend/src/middleware" \
  "portal-frontend/src/modules/mood" \
  "portal-frontend/src/modules/snake-java/components" \
  "portal-frontend/src/modules/tetris-py/components" \
  "portal-frontend/src/modules/blog" \
  "portal-frontend/src/views" \
  "portal-frontend/src/router" \
  "portal-frontend/src/registry"

mkf "portal-frontend/src/layouts/MainLayout.vue"
mkf "portal-frontend/src/layouts/AuthLayout.vue"
mkf "portal-frontend/src/layouts/ModuleLayout.vue"
mkf "portal-frontend/src/middleware/authGuard.js"

mkf "portal-frontend/src/modules/mood/MoodMain.vue"
mkf "portal-frontend/src/modules/mood/router.js"

mkf "portal-frontend/src/modules/snake-java/SnakeMain.vue"
# components dir already created

mkf "portal-frontend/src/modules/tetris-py/TetrisMain.vue"
# components dir already created

mkf "portal-frontend/src/modules/blog/BlogView.vue"

mkf "portal-frontend/src/views/Dashboard.vue"
mkf "portal-frontend/src/views/Login.vue"

mkf "portal-frontend/src/router/index.js"
mkf "portal-frontend/src/registry/featureList.js"
mkf "portal-frontend/src/App.vue"
mkf "portal-frontend/src/main.js"
mkf "portal-frontend/vite.config.js"

# -------------------------
# portal-backend (Spring Boot)
# -------------------------
mkd \
  "portal-backend/src/main/java/com/drzhao/portal/common" \
  "portal-backend/src/main/java/com/drzhao/portal/infrastructure/config" \
  "portal-backend/src/main/java/com/drzhao/portal/infrastructure/security" \
  "portal-backend/src/main/java/com/drzhao/portal/modules/auth" \
  "portal-backend/src/main/java/com/drzhao/portal/modules/game" \
  "portal-backend/src/main/java/com/drzhao/portal/modules/blog" \
  "portal-backend/src/main/resources/db" \
  "portal-backend/data"

mkf "portal-backend/src/main/java/com/drzhao/portal/modules/auth/AuthController.java"
mkf "portal-backend/src/main/java/com/drzhao/portal/modules/auth/AuthService.java"
mkf "portal-backend/src/main/java/com/drzhao/portal/modules/auth/UserRepository.java"

mkf "portal-backend/src/main/java/com/drzhao/portal/modules/game/SnakeController.java"
mkf "portal-backend/src/main/java/com/drzhao/portal/modules/game/SnakeService.java"
mkf "portal-backend/src/main/java/com/drzhao/portal/modules/game/ScoreRepository.java"

mkf "portal-backend/src/main/resources/db/schema.sql"
mkf "portal-backend/src/main/resources/db/data.sql"
mkf "portal-backend/src/main/resources/application.yml"
mkf "portal-backend/pom.xml"

# -------------------------
# ai-services (FastAPI)
# -------------------------
mkd \
  "ai-services/app/database" \
  "ai-services/app/core" \
  "ai-services/app/routers" \
  "ai-services/app/routers/tetris.py.d" \
  "ai-services/app/schemas" \
  "ai-services/data"

# Note: 你给的结构里是 routers/tetris.py 目录下还有 logic.py/crud.py
# 更常见是 routers/tetris/logic.py，但我严格按你贴的“tetris.py 下包含子文件”的表示来建一个子目录
# 用 routers/tetris.py.d 来承载，避免和 tetris.py 文件同名冲突（文件/目录不能同名）
mkf "ai-services/app/database/session.py"
mkf "ai-services/app/routers/mood.py"
mkf "ai-services/app/routers/tetris.py"
mkf "ai-services/app/routers/tetris.py.d/logic.py"
mkf "ai-services/app/routers/tetris.py.d/crud.py"
mkf "ai-services/app/main.py"
mkf "ai-services/requirements.txt"

# -------------------------
# Done
# -------------------------
echo "✅ Project structure generated at: ${TARGET_DIR}"
echo "   You can inspect with: tree -L 4 ${TARGET_DIR}"
