import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router'
import { 
  // 按需导入需要的组件
  NButton,
  NCard,
  NLayout,
  NLayoutSider,
  NLayoutContent,
  NLayoutHeader,
  NLayoutFooter,
  NMenu,
  NBreadcrumb,
  NBreadcrumbItem,
  NSpace,
  NIcon,
  NConfigProvider,
  zhCN
} from 'naive-ui'

const app = createApp(App)

// 使用路由
app.use(router)

// 注册Naive UI组件
app.use(NConfigProvider, {
  locale: zhCN
})

// 注册组件
const components = [
  NButton,
  NCard,
  NLayout,
  NLayoutSider,
  NLayoutContent,
  NLayoutHeader,
  NLayoutFooter,
  NMenu,
  NBreadcrumb,
  NBreadcrumbItem,
  NSpace,
  NIcon
]

// 组件注册
components.forEach(component => {
  const componentName = component.displayName || component.name;
  app.component(componentName, component);
});

app.mount('#app')
