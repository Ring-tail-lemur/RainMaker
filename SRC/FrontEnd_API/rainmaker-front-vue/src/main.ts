import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

import './assets/main.css'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap"

const app = createApp(App)

// app.config.globalProperties.defaultURL = "http://localhost:8080";
// app.config.globalProperties.defaultURL = "https://spring-api-server.azurewebsites.net";
// app.config.globalProperties.clientId = "8189c16057d124b9324e"; // TEST 서버
// app.config.globalProperties.endURL = "/login/oauth2/code/github";
// app.config.globalProperties.clientId = "42286a47489496b3129b"; // 실서버
app.config.globalProperties = {
    // myURL : "http://127.0.0.1:3000",
    myURL : "https://victorious-forest-095d4a310.1.azurestaticapps.net",
    // defaultURL : "http://127.0.0.1:8080",
    defaultURL : "https://spring-api-server.azurewebsites.net",
    // clientId : "8189c16057d124b9324e",
    clientId : "42286a47489496b3129b",
    endURL : "/login/oauth2/code/github"
}
app.use(router)
app.mount('#app')
/** test1 */