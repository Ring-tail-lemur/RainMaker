import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

import './assets/main.css'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap"

const app = createApp(App)

app.config.globalProperties.defaultURL = "http://localhost:8080";
app.use(router)
app.mount('#app')
/** test1 */