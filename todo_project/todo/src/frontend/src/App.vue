<template>
  <v-app>
    <app-bar v-if="isLoginPage()" />
    <side-bar v-if="isLoginPage()" />
    <v-main>
      <router-view></router-view>
    </v-main>
  </v-app>
</template>

<script>
// import request from 'request'
// import {mapState} from 'vuex'

export default {
  name: "App",
  components: {
    SideBar: () => import("@/components/core/SideBar.vue"),
    AppBar: () => import("@/components/core/AppBar.vue"),
  },
  computed: {},
  methods: {
    /* 로그인 페이지에서는 Header, Navigator 숨기기 */
    isLoginPage() {
      return location.pathname !== "/login";
    },
  },
};
/* 
  components: {
  },

  data: () => ({
    //
  }),
  mounted(){
    console.log('test1')
    request('http://localhost:8080/api/hello', function(error, response, body){
      window.console.log('error:', error);
      window.console.log('statusCode:', response && response.statusCode);
      window.console.log('body:', body);
    });
  }
}
 */
</script>
<style scoped>
* {
  background: #ffffff;
}
</style>
