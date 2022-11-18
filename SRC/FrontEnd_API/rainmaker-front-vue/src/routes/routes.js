import DashboardLayout from '../components/Dashboard/Layout/DashboardLayout.vue'
// GeneralViews
import NotFound from '../components/GeneralViews/NotFoundPage.vue'
// Dashboard pages
const Overview = () => import(/* webpackChunkName: "widgets" */ 'src/components/Dashboard/Views/Dashboard/Overview.vue')
const Widgets = () => import(/* webpackChunkName: "widgets" */ 'src/components/Dashboard/Views/Dashboard/Widgets.vue')

// Pages
import User from 'src/components/Dashboard/Views/Pages/UserProfile.vue'
import TimeLine from 'src/components/Dashboard/Views/Pages/TimeLinePage.vue'
import Login from 'src/components/Dashboard/Views/Pages/Login.vue'
import Register from 'src/components/Dashboard/Views/Pages/Register.vue'
import Lock from 'src/components/Dashboard/Views/Pages/Lock.vue'

// Components pages
import Buttons from 'src/components/Dashboard/Views/Components/Buttons.vue'
import GridSystem from 'src/components/Dashboard/Views/Components/GridSystem.vue'
import Panels from 'src/components/Dashboard/Views/Components/Panels.vue'
import SweetAlert from 'src/components/Dashboard/Views/Components/SweetAlert.vue'
import Notifications from 'src/components/Dashboard/Views/Components/Notifications.vue'
import Icons from 'src/components/Dashboard/Views/Components/Icons.vue'
import Typography from 'src/components/Dashboard/Views/Components/Typography.vue'

// Forms pages
const RegularForms  = () => import(/* webpackChunkName: "forms" */ 'src/components/Dashboard/Views/Forms/RegularForms.vue')
const ExtendedForms = () => import(/* webpackChunkName: "forms" */ 'src/components/Dashboard/Views/Forms/ExtendedForms.vue');
const ValidationForms = () => import(/* webpackChunkName: "forms" */ 'src/components/Dashboard/Views/Forms/ValidationForms.vue')
const Wizard = () => import(/* webpackChunkName: "forms" */ 'src/components/Dashboard/Views/Forms/Wizard.vue');

// TableList pages
const RegularTables = () => import(/* webpackChunkName: "tables" */ 'src/components/Dashboard/Views/Tables/RegularTables.vue');
const ExtendedTables = () => import(/* webpackChunkName: "tables" */ 'src/components/Dashboard/Views/Tables/ExtendedTables.vue');
const PaginatedTables = () => import(/* webpackChunkName: "tables" */ 'src/components/Dashboard/Views/Tables/PaginatedTables.vue');
// Maps pages
const GoogleMaps = () => import(/* webpackChunkName: "maps" */ 'src/components/Dashboard/Views/Maps/GoogleMaps.vue')
const FullScreenMap = () => import(/* webpackChunkName: "maps" */ 'src/components/Dashboard/Views/Maps/FullScreenMap.vue')
const VectorMaps = () => import(/* webpackChunkName: "maps" */ 'src/components/Dashboard/Views/Maps/VectorMapsPage.vue');

// Calendar
import Calendar from 'src/components/Dashboard/Views/Calendar/CalendarRoute.vue'
import repositorySelect from "@/components/Dashboard/Views/Custom/RepositorySelect";
import TokenSetting from "@/components/Dashboard/Views/Pages/TokenSetting";
import SlackWebhookRegister from "@/components/Dashboard/Views/Pages/SlackWebhookRegister";
import axios from "axios";
import setHeaderJWT from "@/api/setHeaderJWT";
import pageCheckAndChange from "@/util/pageCheckAndChange";
// Charts
const Charts = () => import(/* webpackChunkName: "widgets" */ 'src/components/Dashboard/Views/Charts.vue')

// Custom
const LoginMiddleWare = () => import(/* webpackChunkName: "widgets" */ 'src/components/Dashboard/Views/Custom/loginMiddleWare.vue')
const RepositorySelect = () => import(/* webpackChunkName: "widgets" */ 'src/components/Dashboard/Views/Custom/RepositorySelect.vue')
const DashBoard = () => import(/* webpackChunkName: "widgets" */ 'src/components/Dashboard/Views/Custom/Dashboard.vue')
const MainPage = () => import(/* webpackChunkName: "widgets" */ '@/components/Dashboard/Views/Custom/MainPage/MainPage.vue');
const LeadTimeForChangeDetails = () => import(/* webpackChunkName: "widgets" */ '@/components/Dashboard/Views/Custom/LeadTimeForChangeComponent/LeadTimeForChangeDetails.vue');
const ChangeFailureRateDetails = () => import(/* webpackChunkName: "widgets" */ '@/components/Dashboard/Views/Custom/ChangeFailureRateComponent/ChangeFailureRateDetails');
const MeanTimeToRepairDetails = () => import('@/components/Dashboard/Views/Custom/MeanTimeTORepairComponent/MeanTimeToRepairDetails.vue')
const DeploymentFrequencyDetails = () => import(/* webpackChunkName: "widgets" */ '@/components/Dashboard/Views/Custom/DeploymentFrequencyComponent/DeploymentFrequencyDetails');

const requireAuth =  () => async (to, from, next) => {
  await pageCheckAndChange(to, from, next)
}

let componentsMenu = {
  path: '/components',
  component: DashboardLayout,
  redirect: '/components/buttons',
  children: [
    {
      path: 'buttons',
      name: 'Buttons',
      component: Buttons
    },
    {
      path: 'grid-system',
      name: 'Grid System',
      component: GridSystem
    },
    {
      path: 'panels',
      name: 'Panels',
      component: Panels
    },
    {
      path: 'sweet-alert',
      name: 'Sweet Alert',
      component: SweetAlert
    },
    {
      path: 'notifications',
      name: 'Notifications',
      component: Notifications
    },
    {
      path: 'icons',
      name: 'Icons',
      component: Icons
    },
    {
      path: 'typography',
      name: 'Typography',
      component: Typography
    }

  ]
}
let formsMenu = {
  path: '/forms',
  component: DashboardLayout,
  redirect: '/forms/regular',
  children: [
    {
      path: 'regular',
      name: 'Regular Forms',
      component: RegularForms
    },
    {
      path: 'extended',
      name: 'Extended Forms',
      component: ExtendedForms
    },
    {
      path: 'validation',
      name: 'Validation Forms',
      component: ValidationForms
    },
    {
      path: 'wizard',
      name: 'Wizard',
      component: Wizard
    }
  ]
}

let tablesMenu = {
  path: '/table-list',
  component: DashboardLayout,
  redirect: '/table-list/regular',
  children: [
    {
      path: 'regular',
      name: 'Regular Tables',
      component: RegularTables
    },
    {
      path: 'extended',
      name: 'Extended Tables',
      component: ExtendedTables
    },
    {
      path: 'paginated',
      name: 'Paginated Tables',
      component: PaginatedTables
    }]
}

let mapsMenu = {
  path: '/maps',
  component: DashboardLayout,
  redirect: '/maps/google',
  children: [
    {
      path: 'google',
      name: 'Google Maps',
      component: GoogleMaps
    },
    {
      path: 'full-screen',
      name: 'Full Screen Map',
      component: FullScreenMap
    },
    {
      path: 'vector-map',
      name: 'Vector Map',
      component: VectorMaps
    }
  ]
}

let pagesMenu = {
  path: '/pages',
  component: DashboardLayout,
  redirect: '/pages/user',
  children: [
    {
      path: 'user',
      name: 'User Page',
      component: User
    },
    {
      path: 'timeline',
      name: 'Timeline Page',
      component: TimeLine
    }
  ]
}

let loginPage = {
  path: '/login',
  name: 'Login',
  component: Login
}

let mainPage = {
  path: '/main',
  name: 'MainPage',
  component: MainPage
}

let githubTokenSettingPage = {
  path: '/register/token',
  name: 'TokenSetting',
  component: TokenSetting,
  beforeEnter: requireAuth()
}

let slackWebhookRegisterPage = {
  path: '/user/SlackWebhookRegister',
  name: 'SlackWebhookRegister',
  component: SlackWebhookRegister,
  beforeEnter: requireAuth()
}

let registerPage = {
  path: '/register',
  name: 'Register',
  component: Register
}

let lockPage = {
  path: '/lock',
  name: 'Lock',
  component: Lock
}

let chartPage = {
  path: '/charts',
  name: 'charts',
  component: Charts
}

let loginMiddleWarePage = {
  path: '/login/oauth2/code/github',
  name: 'LoginMiddleWare',
  component: LoginMiddleWare
}

let RepositorySelectPage = {
  path: '/RepositorySelect',
  component: DashboardLayout,
  meta: {
    keepAlive: false
  },
  children: [
    {
      path: '',
      name: 'RepositorySelect',
      component: RepositorySelect
    }
  ],
  beforeEnter: requireAuth()
}

let DashBoardPage = {
  path: '/dashboard',
  component: DashboardLayout,
  children: [
    {
      path: '',
      name: 'DashBoard',
      component: DashBoard
    }
  ],
  beforeEnter: requireAuth()
}

let LeadTimeForChangeDetailPage = {
  path: '/dashboard/lead-time-for-change',
  component: DashboardLayout,
  children : [
    {
      path: '',
      name: 'LeadTimeForChangeDetails',
      component : LeadTimeForChangeDetails
    }
  ],
  beforeEnter: requireAuth()
}

let ChangeFailureRateDetailPage = {
  path: '/dashboard/change-failure-rate',
  component: DashboardLayout,
  children : [
    {
      path: '',
      name: 'LeadTimeForChangeDetails',
      component : ChangeFailureRateDetails
    }
  ],
  beforeEnter: requireAuth()
}


let MeanTimeToRepairDetailPage = {
  path: '/dashboard/mean-time-to-repair',
  component: DashboardLayout,
  children : [
    {
      path: '',
      name: 'MeanTimeToRepairDetails',
      component : MeanTimeToRepairDetails
    }
  ],
  beforeEnter: requireAuth()
}

let DeploymentFrequencyDetailPage = {
  path: '/dashboard/deployment-frequency',
  component: DashboardLayout,
  children : [
    {
      path: '',
      name: 'DeploymentFrequencyDetails',
      component : DeploymentFrequencyDetails
    }
  ]
}

let CalendarPage = {
  path: '/calendar',
  component: Calendar
}

let WidgetPage = {
  path: '/admin/widget',
  component: Widgets
}

const routes = [
  {
    path: '/',
    redirect: '/main'
  },
  CalendarPage,
  slackWebhookRegisterPage,
  LeadTimeForChangeDetailPage,
  DeploymentFrequencyDetailPage,
  ChangeFailureRateDetailPage,
  mainPage,
  componentsMenu,
  chartPage,
  formsMenu,
  tablesMenu,
  mapsMenu,
  pagesMenu,
  loginPage,
  registerPage,
  lockPage,
  loginMiddleWarePage,
  RepositorySelectPage,
  DashBoardPage,
  githubTokenSettingPage,
  WidgetPage,
  MeanTimeToRepairDetailPage,
  {path: '*', component: NotFound}
];

export default routes
