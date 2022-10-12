import axios from "axios";
import setHeaderJWT from "@/api/setHeaderJWT";

async function pageCheckAndChange(to, from, next) {
  try {
    let response = await axios.get("http://localhost:8080" + "/api/check", {headers: setHeaderJWT()});
  } catch (error) {
    if (error.request.status === 401) {
      console.log(location.pathname && to.path !== '/login');
      next('/login')
    }
    if (error.response.status === 442 && to.path !== '/register/token') {
      next('/register/token');
    }
    if (error.response.status === 443 && to.path !== '/RepositorySelect') {
      next('/RepositorySelect');
      console.log(next)
    }
    if (error.response.status === 445 && to.path !== '/fakePage/CallAdminPlease') {
      next('/fakePage/CallAdminPlease')
    }
    if (error.response.status === 444 && to.path !== '/dashboard') {
      next('/dashboard')
    }
  }
  next();
}


export default pageCheckAndChange;
