<template>
  <div class="min-h-screen bg-white p-4">
    <h1 class="text-xl font-semibold mb-4">纯内容页面示例</h1>
    <p class="text-gray-600 mb-4">没有头部和底部导航，内容占满整个视口。</p>
    <div class="space-y-4">
      <div class="h-32 bg-gray-100 rounded"></div>
      <div class="h-32 bg-gray-100 rounded"></div>
      <div class="h-32 bg-gray-100 rounded"></div>
      <div class="h-32 bg-gray-100 rounded"></div>
    </div>
    <div class="mt-6">
      <router-link to="/h5-with-nav" class="text-blue-600">返回带导航示例</router-link>
    </div>
  </div>
</template>
