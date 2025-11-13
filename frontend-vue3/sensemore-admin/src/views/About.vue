<template>
  <n-layout class="min-h-full">
    <n-layout-header class="bg-white border-b shadow-sm flex items-center justify-between px-6">
      <div class="flex items-center gap-2">
        <span class="text-xl font-bold text-blue-600">Sensemore Admin</span>
      </div>
    </n-layout-header>
    <n-layout has-sider>
      <n-layout-sider
        bordered
        collapsible
        :collapsed-width="64"
        :width="240"
        show-trigger
        class="bg-white"
      >
        <n-menu
          :options="menuOptions"
          :value="activePath"
          @update:value="handleMenuUpdate"
          mode="vertical"
          class="h-full"
        />
      </n-layout-sider>
      <n-layout class="p-6">
        <n-breadcrumb>
          <n-breadcrumb-item>首页</n-breadcrumb-item>
          <n-breadcrumb-item>关于我们</n-breadcrumb-item>
        </n-breadcrumb>
        <n-card class="mt-4">
          <template #header>
            <h2 class="text-xl font-bold">关于 Sensemore Admin</h2>
          </template>
          <div class="p-6">
            <div class="flex flex-col md:flex-row items-center gap-8">
              <div class="w-32 h-32 bg-blue-500 rounded-full flex items-center justify-center text-white">
                <NIcon size="64">
                  <Grid />
                </NIcon>
              </div>
              <div class="flex-1">
                <h3 class="text-2xl font-bold text-blue-600 mb-4">Sensemore Admin 管理系统</h3>
                <p class="text-gray-600 mb-4">
                  Sensemore Admin 是一个基于 Vue 3 + Tailwind CSS 4 + Naive UI 构建的现代化管理系统框架。
                  它提供了丰富的组件和灵活的布局，帮助开发者快速构建高质量的管理界面。
                </p>
                <div class="grid grid-cols-1 md:grid-cols-3 gap-4 mb-4">
                  <div class="bg-gray-50 p-4 rounded-lg">
                    <p class="text-sm text-gray-500">技术栈</p>
                    <p class="font-medium flex items-center gap-2">
                    <NIcon size="16">
                      <CodeSlash />
                    </NIcon>
                    Vue 3 + Tailwind CSS 4 + Naive UI
                  </p>
                  </div>
                  <div class="bg-gray-50 p-4 rounded-lg">
                    <p class="text-sm text-gray-500">版本号</p>
                    <p class="font-medium">1.0.0-beta</p>
                  </div>
                  <div class="bg-gray-50 p-4 rounded-lg">
                    <p class="text-sm text-gray-500">许可证</p>
                    <p class="font-medium">MIT License</p>
                  </div>
                </div>
                <n-button type="primary" class="flex items-center gap-2">
                  <NIcon size="16">
                    <AlertCircle />
                  </NIcon>
                  了解更多
                </n-button>
              </div>
            </div>
            <div class="mt-8 border-t pt-6">
              <h3 class="text-xl font-bold mb-4">功能特性</h3>
              <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
                <n-card>
                  <template #header>
                    <n-avatar round style="--n-avatar-size: 32px; background-color: #3b82f6;">
                  <NIcon size="18">
                    <Layers />
                  </NIcon>
                </n-avatar>
                  </template>
                  <p class="font-medium mb-2">现代化架构</p>
                  <p class="text-sm text-gray-500">
                    基于 Vue 3 Composition API 和 TypeScript，提供更高效的开发体验。
                  </p>
                </n-card>
                <n-card>
                  <template #header>
                    <n-avatar round style="--n-avatar-size: 32px; background-color: #10b981;">
                  <NIcon size="18">
                    <Grid />
                  </NIcon>
                </n-avatar>
                  </template>
                  <p class="font-medium mb-2">优雅的 UI 设计</p>
                  <p class="text-sm text-gray-500">
                    结合 Tailwind CSS 和 Naive UI，打造现代化、美观的界面。
                  </p>
                </n-card>
                <n-card>
                  <template #header>
                    <n-avatar round style="--n-avatar-size: 32px; background-color: #f59e0b;">
                  <NIcon size="18">
                    <Cog />
                  </NIcon>
                </n-avatar>
                  </template>
                  <p class="font-medium mb-2">丰富的组件库</p>
                  <p class="text-sm text-gray-500">
                    提供大量开箱即用的组件，满足各种业务场景需求。
                  </p>
                </n-card>
              </div>
            </div>
          </div>
        </n-card>
      </n-layout>
    </n-layout>
  </n-layout>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { NIcon } from 'naive-ui'
import { AlertCircle, CodeSlash, Grid, Layers, Cog } from '@vicons/ionicons5'

const route = useRoute()
const activePath = ref('/about')

const menuOptions = [
  {
    key: '/dashboard',
    label: '控制面板'
  },
  {
    key: '/about',
    label: '关于我们'
  },
  {
    key: 'system',
    label: '系统设置',
    children: [
      {
        key: '/system/basic',
        label: '基础设置'
      },
      {
        key: '/system/logs',
        label: '系统日志'
      }
    ]
  }
]

const handleMenuUpdate = (key) => {
  activePath.value = key
}

onMounted(() => {
  activePath.value = route.path
})
</script>

<style scoped>
/* 可以添加组件特定的样式 */
</style>