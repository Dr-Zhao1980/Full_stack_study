<template>
  <div class="dashboard-view">
    <header class="hero">
      <h1>👋 欢迎回来, Dr. Zhao</h1>
      <p>今日系统状态: <span class="status">Online</span></p>
    </header>

    <div class="features-grid">
      <div 
        v-for="feature in features" 
        :key="feature.id"
        class="feature-card"
        @click="navigateTo(feature)"
      >
        <div class="card-icon">{{ feature.icon }}</div>
        <div class="card-content">
          <h3>{{ feature.title }}</h3>
          <p>{{ feature.description }}</p>
        </div>
        <div class="card-footer">
          <span :class="['badge', feature.backend]">{{ feature.backend }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { features } from '@/registry/featureList'
import { useRouter } from 'vue-router'

const router = useRouter()

const navigateTo = (feature) => {
  router.push(feature.path)
}
</script>

<style scoped>
.hero { margin-bottom: 40px; }
.hero h1 { font-size: 2.5rem; color: #2c3e50; }
.status { color: #42b983; font-weight: bold; }

.features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 25px;
}

.feature-card {
  background: white;
  border-radius: 16px;
  padding: 25px;
  box-shadow: 0 4px 20px rgba(0,0,0,0.05);
  cursor: pointer;
  transition: all 0.3s ease;
  border: 1px solid transparent;
  display: flex;
  flex-direction: column;
}

.feature-card:hover {
  transform: translateY(-5px);
  border-color: #42b983;
  box-shadow: 0 8px 30px rgba(66, 185, 131, 0.15);
}

.card-icon { font-size: 3rem; margin-bottom: 15px; }
.card-content h3 { margin: 0 0 10px 0; color: #2c3e50; }
.card-content p { color: #666; font-size: 0.9rem; line-height: 1.5; flex-grow: 1; }

.card-footer { margin-top: 20px; display: flex; justify-content: flex-end; }
.badge { font-size: 0.75rem; padding: 4px 10px; border-radius: 20px; color: white; text-transform: uppercase; font-weight: bold; }
.badge.java { background-color: #e67e22; }
.badge.python { background-color: #3498db; }
</style>