<template>
  <div class="auth-container">
    <div class="auth-card">
      <div class="brand">
        <span class="logo">🌌</span>
        <h1>Personal Portal</h1>
      </div>
      <router-view />
    </div>
  </div>
</template>

<style scoped>
.auth-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 100%);
  color: white;
}

.auth-card {
  width: 100%;
  max-width: 400px;
  background: rgba(255, 255, 255, 0.1);
  padding: 40px;
  border-radius: 16px;
  backdrop-filter: blur(10px);
  box-shadow: 0 8px 32px 0 rgba(0, 0, 0, 0.37);
  border: 1px solid rgba(255, 255, 255, 0.18);
  text-align: center;
}

.brand h1 {
  font-size: 1.5rem;
  margin-bottom: 30px;
  font-weight: 300;
  letter-spacing: 2px;
}

.logo {
  font-size: 3rem;
  display: block;
  margin-bottom: 10px;
}
</style>