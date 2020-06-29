<template>
  <v-app>
    <app-bar />
    <side-bar />
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
  background: #e8f5ff;
}
</style>
