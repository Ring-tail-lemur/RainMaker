<template>
  <div class="register-page">
    <app-navbar></app-navbar>
    <div class="wrapper wrapper-full-page ">
      <div class="full-page register-page section-image" filter-color="black">
        <div class="content">
          <div class="container">
            <div class="row">
              <div class="col-lg-5 col-md-5 ml-auto">
                <info-section class="mt-5"
                              type="primary"
                              title="DORA Metrics 분석"
                              description="DevOps 연구평가(Google, DORA 연구팀)에서는 개발조직이 얼마나 잘 일하고있는지를 측정할 수 있는 고수준 측정지표로 DORA Metrics를 지목했습니다."
                              icon="nc-icon nc-check-2">
                </info-section>

                <info-section type="primary"
                              title="위험 감지 알람"
                              description="리뷰 없는 병합, 너무 큰 커밋, 번 아웃과 같은 위험을 감지하고 알려주는 일은 팀의 위험 예방에도, 개인의 성장에도 중요한 요소입니다."
                              icon="nc-icon nc-check-2">
                </info-section>

                <info-section type="primary"
                              title="팀의 과거, 현재, 그리고 성장"
                              description="팀의 성장을 두 눈으로 보는 일은 팀원들의 동기부여 뿐만이 아니라 팀의 가속도를 확인하고 성장 전략을 세울 수 있습니다."
                              icon="nc-icon nc-check-2">
                </info-section>
              </div>
              <div class="col-lg-4 col-md-6 mr-auto">
                <card type="signup" class="text-center">
                  <template slot="header">
                    <h4 class="card-title">회원가입</h4>
                  </template>


                  <p-button slot="footer" type="info" round>Get Started</p-button>
                </card>
              </div>
            </div>
          </div>
        </div>
        <app-footer></app-footer>
        <div class="full-page-background"
             style="background-image: url(static/img/background/jan-sendereks.jpg) "></div>
      </div>
    </div>
  </div>
</template>
<script>
  import AppNavbar from './Layout/AppNavbar'
  import AppFooter from './Layout/AppFooter'
  import { Card, Checkbox, Button, InfoSection } from 'src/components/UIComponents';

  export default {
    components: {
      Card,
      AppNavbar,
      AppFooter,
      InfoSection,
      [Checkbox.name]: Checkbox,
      [Button.name]: Button
    },
    data(){
      return {
        form: {
          firstName: '',
          lastName: '',
          email: '',
          acceptTerms: true
        }
      }
    },
    methods: {
      toggleNavbar() {
        document.body.classList.toggle('nav-open')
      },
      closeMenu() {
        document.body.classList.remove('nav-open')
        document.body.classList.remove('off-canvas-sidebar')
      }
    },
    beforeDestroy() {
      this.closeMenu()
    }
  }
</script>
<style>
</style>
