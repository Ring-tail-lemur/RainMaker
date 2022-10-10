function pageCheckAndChange(error, vue) {
  console.log("pageCheckAndChange");
  console.log(error.response.status);
  if(error.response.status === 401) {
    vue.$emit('notLogin')
  }
  else if(error.response.status === 444) {
    vue.$emit('waiting');
  }
  else if(error.response.status === 442) {
    vue.$emit('tokenRegister');
  }
  else if(error.response.status === 443) {
    vue.$emit('repositorySelect');
  }
  else {
    console.error("예상치 못한 오류, 코드번호 : " + error.response.status );
  }
}


export default pageCheckAndChange;
