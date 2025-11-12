<script setup>
import { ref } from 'vue'

defineProps({
  msg: String,
})

const count = ref(0)
</script>

<template>
  <h1 class="text-2xl font-semibold">{{ msg }}</h1>

  <div class="p-8">
    <button type="button" @click="count++" class="px-4 py-2 rounded-md bg-blue-600 text-white hover:bg-blue-700">count is {{ count }}</button>
    <p class="mt-3">
      Edit
      <code>components/HelloWorld.vue</code> to test HMR
    </p>
  </div>

  <p>
    Check out
    <a href="https://vuejs.org/guide/quick-start.html#local" target="_blank"
      >create-vue</a
    >, the official Vue + Vite starter
  </p>
  <p>
    Learn more about IDE Support for Vue in the
    <a
      href="https://vuejs.org/guide/scaling-up/tooling.html#ide-support"
      target="_blank"
      >Vue Docs Scaling up Guide</a
    >.
  </p>
  <p class="text-gray-500">Click on the Vite and Vue logos to learn more</p>
</template>
