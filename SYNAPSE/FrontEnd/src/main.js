import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import { useAuthStore } from './store/Authstore'
import './style.css'

const app = createApp(App)
const pinia = createPinia()

app.use(pinia)
// Cria a store de login já na inicialização: ela entrega o token ao axios (configurarApi),
// então qualquer página aberta direto (ex.: /sidebar/upload) já envia o Authorization.
useAuthStore(pinia)
app.use(router)
app.mount('#app')
