const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  // avatar: state => state.user.avatar,
  // 用户这里暂时只用到用户民和id
  username: state => state.user.username,
  id: state => state.user.id
}
export default getters
