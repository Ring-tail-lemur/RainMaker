import axios from "axios";
import setHeaderJWT from "@/api/setHeaderJWT";
import URLConfig from '../URLconfig.json';

async function pageCheckAndChange(to, from, next) {

  const defaultURL = URLConfig.defaultURL;

  try {
    let response = await axios.get(defaultURL + "/api/check", {headers: setHeaderJWT()});
  } catch (error) {
    if (error.request.status === 401) {
      next('/login')
    }
    if (error.response.status === 442 && to.path !== '/register/token') {
      if(from.path === '/register/token'){
        location.reload();
      }
      else {
        next('/register/token');
      }
    }
    if (error.response.status === 443 && to.path !== '/RepositorySelect') {
      if(from.path === '/RepositorySelect'){
        location.reload();
      }
      else {
        alert("리포지토리를 먼저 선택해주세요!")
        next('/RepositorySelect');
      }
    }
    if (error.response.status === 445 && to.path !== '/fakePage/CallAdminPlease') {
      if(from.path === '/fakePage/CallAdminPlease'){
        location.reload();
      }
      else {
        next('/fakePage/CallAdminPlease');
      }
    }
    if (error.response.status === 444 && to.path !== '/dashboard') {
      if(from.path === '/dashboard'){
        location.reload();
      }
      else {
        next('/dashboard');
      }
    }
  }
  next();
}


export default pageCheckAndChange;
