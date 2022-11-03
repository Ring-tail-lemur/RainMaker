export default [
  {
    name: 'USER',
    icon: 'nc-icon nc-book-bookmark',
    children: [
      {
        name: 'Login',
        path: '/login'
      },
      {
        name: 'Register',
        path: '/register'
      },
      {
        name: 'Token',
        path: '/register/token'
      }
    ]
  },
  {
    name: 'Dashboard',
    icon: 'nc-icon nc-bank',
    path: '/dashboard'
  },
  {
    name: 'Settings',
    icon: 'nc-icon nc-pin-3',
    children: [
      {
        name: 'Repository 선택',
        path: '/RepositorySelect'
      },
      {
        name: 'Slack URL 등록',
        path: '/user/SlackWebhookRegister'
      },
    ]
  },
  {
    name: 'Calendar',
    icon: 'nc-icon nc-calendar-60',
    path: '/calendar'
  }
]
