export const features = [
  { 
    id: 'mood', 
    title: 'AI 心情气象台', 
    description: '分析你的文本情绪', 
    icon: '🌤️', 
    path: '/modules/mood', 
    requireAuth: true,
    backend: 'python' // 标记后端类型，仅用于展示或逻辑判断
  },
  { 
    id: 'snake', 
    title: 'Java 贪吃蛇', 
    description: '经典游戏，Java记录分数', 
    icon: '🐍', 
    path: '/modules/snake', 
    requireAuth: true,
    backend: 'java'
  },
  { 
    id: 'tetris', 
    title: 'Python 俄罗斯方块', 
    description: 'Python 辅助的方块游戏', 
    icon: '🧱', 
    path: '/modules/tetris', 
    requireAuth: false, // 允许游客访问
    backend: 'python'
  },
  {
    id: 'blog',
    title: '技术博客',
    description: '我的全栈学习之路',
    icon: '📝',
    path: '/modules/blog',
    requireAuth: false,
    backend: 'java'
  }
]