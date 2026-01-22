<template>
  <div class="module-layout">
    <header class="module-header">
      <button @click="$router.push('/')" class="back-btn">⬅ 返回仪表盘</button>
      <span class="module-title">功能运行中</span>
    </header>
    <div class="module-content">
      <router-view />
    </div class="module-content">
  </div>
</template>

<style scoped>
.module-layout { height: 100vh; display: flex; flex-direction: column; }
.module-header { padding: 15px 30px; background: #fff; border-bottom: 1px solid #eee; display: flex; align-items: center; }
.back-btn { padding: 8px 16px; background: #333; color: white; border: none; border-radius: 4px; cursor: pointer; }
.module-content { flex: 1; padding: 20px; background: #fafafa; display: flex; justify-content: center; align-items: center; }
</style>