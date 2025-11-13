<template>
  <n-layout class="min-h-full bg-gray-100">
    <n-layout-header class="bg-blue-600 text-white border-b shadow-sm flex items-center justify-between px-6">
      <div class="flex items-center gap-2">
        <span class="text-xl font-bold text-blue-600">Sensemore Admin</span>
      </div>
      <div class="flex items-center gap-4">
        <n-button size="small" type="default">通知</n-button>
        <n-button size="small" type="default" round>管理员</n-button>
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
          <n-breadcrumb-item>控制面板</n-breadcrumb-item>
        </n-breadcrumb>
        <div class="mt-4 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
          <n-card>
            <div class="flex justify-between items-center">
              <div>
                <p class="text-sm text-gray-500">总用户数</p>
                <p class="text-2xl font-bold mt-1">1,280</p>
                <p class="text-xs text-green-500 mt-1">↑ 12.5% 较上月</p>
              </div>
              <n-avatar round text="用户" style="--n-avatar-size: 48px; background-color: #4ade80;"></n-avatar>
            </div>
          </n-card>
          <n-card>
            <div class="flex justify-between items-center">
              <div>
                <p class="text-sm text-gray-500">今日访问</p>
                <p class="text-2xl font-bold mt-1">256</p>
                <p class="text-xs text-green-500 mt-1">↑ 8.3% 较昨日</p>
              </div>
              <n-avatar round text="访问" style="--n-avatar-size: 48px; background-color: #60a5fa;"></n-avatar>
            </div>
          </n-card>
          <n-card>
            <div class="flex justify-between items-center">
              <div>
                <p class="text-sm text-gray-500">平均响应时间</p>
                <p class="text-2xl font-bold mt-1">124ms</p>
                <p class="text-xs text-red-500 mt-1">↓ 2.1% 较上周</p>
              </div>
              <n-avatar round text="时间" style="--n-avatar-size: 48px; background-color: #f472b6;"></n-avatar>
            </div>
          </n-card>
          <n-card>
            <div class="flex justify-between items-center">
              <div>
                <p class="text-sm text-gray-500">系统状态</p>
                <p class="text-2xl font-bold mt-1">正常</p>
                <p class="text-xs text-green-500 mt-1">全部服务运行正常</p>
              </div>
              <n-avatar round text="状态" style="--n-avatar-size: 48px; background-color: #22d3ee;"></n-avatar>
            </div>
          </n-card>
        </div>
        <div class="mt-6 grid grid-cols-1 lg:grid-cols-3 gap-6">
          <n-card class="lg:col-span-2">
            <template #header>
              <div class="flex justify-between items-center">
              <h3>访问趋势</h3>
              <n-select size="small" :options="['今日', '本周', '本月']" defaultValue="今日" />
            </div>
            </template>
            <div class="h-80 p-4">
              <!-- 简单的柱状图UI -->
              <div class="h-full flex items-end justify-between">
                <div class="flex flex-col items-center w-1/8">
                  <div class="w-full bg-blue-500 rounded-t h-1/4"></div>
                  <span class="mt-2 text-xs">1月</span>
                </div>
                <div class="flex flex-col items-center w-1/8">
                  <div class="w-full bg-blue-500 rounded-t h-2/4"></div>
                  <span class="mt-2 text-xs">2月</span>
                </div>
                <div class="flex flex-col items-center w-1/8">
                  <div class="w-full bg-blue-500 rounded-t h-3/4"></div>
                  <span class="mt-2 text-xs">3月</span>
                </div>
                <div class="flex flex-col items-center w-1/8">
                  <div class="w-full bg-blue-500 rounded-t h-1/2"></div>
                  <span class="mt-2 text-xs">4月</span>
                </div>
                <div class="flex flex-col items-center w-1/8">
                  <div class="w-full bg-blue-500 rounded-t h-5/6"></div>
                  <span class="mt-2 text-xs">5月</span>
                </div>
                <div class="flex flex-col items-center w-1/8">
                  <div class="w-full bg-blue-500 rounded-t h-2/3"></div>
                  <span class="mt-2 text-xs">6月</span>
                </div>
              </div>
            </div>
          </n-card>
          <n-card>
            <template #header>
              <div class="flex justify-between items-center">
              <h3>用户分布</h3>
            </div>
            </template>
            <div class="h-80 flex items-center justify-center">
              <!-- 简单的饼图UI -->
              <div class="relative w-48 h-48 rounded-full">
                <div class="absolute inset-0 rounded-full overflow-hidden">
                  <div class="absolute top-0 left-0 w-full h-full bg-blue-500" style="clip-path: polygon(50% 50%, 50% 0%, 100% 0%, 100% 50%);"></div>
                  <div class="absolute top-0 left-0 w-full h-full bg-green-500" style="clip-path: polygon(50% 50%, 100% 50%, 100% 100%, 50% 100%);"></div>
                  <div class="absolute top-0 left-0 w-full h-full bg-yellow-500" style="clip-path: polygon(50% 50%, 50% 100%, 0% 100%, 0% 50%);"></div>
                  <div class="absolute top-0 left-0 w-full h-full bg-red-500" style="clip-path: polygon(50% 50%, 0% 50%, 0% 0%, 50% 0%);"></div>
                </div>
                <div class="absolute inset-0 flex items-center justify-center">
                  <div class="w-16 h-16 rounded-full bg-white flex items-center justify-center">
                    <span class="text-sm font-medium">64%</span>
                  </div>
                </div>
              </div>
            </div>
          </n-card>
        </div>
      </n-layout>
    </n-layout>
  </n-layout>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const activePath = ref('/dashboard')

const menuOptions = [
  {
    key: '/dashboard',
    label: '控制面板'
  },
  {
    key: 'users',
    label: '用户管理',
    children: [
      {
        key: '/users/list',
        label: '用户列表'
      },
      {
        key: '/users/roles',
        label: '角色权限'
      }
    ]
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