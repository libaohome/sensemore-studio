<template>
  <div class="h-screen flex flex-col items-center justify-center bg-gray-50">
    <n-card class="w-full max-w-md text-center p-8">
      <n-icon size="96" color="#60a5fa" class="mx-auto mb-4">
        <AlertCircle />
      </n-icon>
      <h1 class="text-4xl font-bold text-gray-800 mb-2">404</h1>
      <h2 class="text-xl font-semibold text-gray-600 mb-4">页面不存在</h2>
      <p class="text-gray-500 mb-6">
        抱歉，您访问的页面不存在或已被移除
      </p>
      <div class="flex justify-center gap-4">
        <n-button type="primary" @click="goHome">返回首页</n-button>
        <n-button type="default" @click="goBack">返回上一页</n-button>
      </div>
    </n-card>
  </div>
</template>

<script setup>
import { AlertCircle } from '@vicons/ionicons5'
import { useRouter } from 'vue-router'

const router = useRouter()

const goHome = () => {
  router.push('/')
}

const goBack = () => {
  router.back()
}
</script>

<style scoped>
/* 可以添加组件特定的样式 */
</style>