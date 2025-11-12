<template>
  <div class="min-h-screen bg-white flex flex-col">
    <header class="sticky top-0 z-10 bg-white border-b">
      <div class="h-12 flex items-center justify-between px-4">
        <div class="text-base font-medium">示例页面</div>
        <div class="flex items-center gap-3">
          <router-link to="/h5-plain" class="text-sm text-blue-600">纯内容</router-link>
        </div>
      </div>
    </header>

    <main class="flex-1 overflow-y-auto px-4 py-4">
      <div class="space-y-4">
        <div class="h-32 bg-gray-100 rounded"></div>
        <div class="h-32 bg-gray-100 rounded"></div>
        <div class="h-32 bg-gray-100 rounded"></div>
        <div class="h-32 bg-gray-100 rounded"></div>
      </div>
    </main>

    <nav class="sticky bottom-0 bg-white border-t">
      <div class="h-14 grid grid-cols-3">
        <a class="flex items-center justify-center text-gray-600 hover:text-blue-600" href="#">首页</a>
        <a class="flex items-center justify-center text-gray-600 hover:text-blue-600" href="#">发现</a>
        <a class="flex items-center justify-center text-gray-600 hover:text-blue-600" href="#">我的</a>
      </div>
    </nav>
  </div>
  
</template>
