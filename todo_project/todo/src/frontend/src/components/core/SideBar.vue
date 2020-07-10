<template>
  <v-navigation-drawer
    id="core-navigation-drawer"
    app
    :dark="true"
    color="rgb(134, 138, 140)"
    :right="$vuetify.rtl"
    permanent
    v-model="drawer"
    :mini-variant.sync="mini"
  >
    <v-divider class="mb-1" />
    <v-list dense nav>
      <v-list-item>
        <v-list-item-avatar class="align-self-center mx-auto" color="white" contain size="35">
          <v-img src="../../assets/inzent-logo.png" />
        </v-list-item-avatar>
        <v-list-item-title class="display-1 text-center">INZENT-TODO</v-list-item-title>
        <v-btn icon @click.stop="mini = !mini">
          <v-icon>mdi-chevron-left</v-icon>
        </v-btn>
      </v-list-item>
    </v-list>
    <v-divider class="mb-2" />

    <v-list expand>
      <v-list-item-group v-model="activeMenu" color="defualt">
        <v-list-item v-for="(menu, i) in menus" :key="`menu-${i + 1}`" :to="menu.to">
          <v-list-item-icon>
            <v-icon v-text="menu.icon"></v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title v-text="menu.text"></v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <div class="v-footer-item">
          <v-list-item to="/profile">
            <v-list-item-icon>
              <v-icon>mdi-account</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>MY PAGE</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-list-item @click.stop="dialog = true">
            <v-list-item-icon>
              <v-icon>mdi-location-exit</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>LOGOUT</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </div>
      </v-list-item-group>
    </v-list>
    <v-dialog v-model="dialog" max-width="290">
      <v-card>
        <v-card-title></v-card-title>
        <v-card-text>로그아웃 하시겠습니까?</v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click.prevent="logout">확인</v-btn>
          <v-btn color="green darken-1" text @click="dialog = false">취소</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-navigation-drawer>
</template>

<script>
import { mapState, mapMutations } from "vuex";

export default {
  data: () => ({
    name: "Sidebar",
    activeMenu: undefined,
    drawer: true,
    mini: false,
    menus: [
      {
        icon: "mdi-view-dashboard",
        text: "DASHBOARD",
        to: "/dashboard"
      },
      {
        icon: "mdi-newspaper-variant-multiple-outline",
        text: "PROJECTS",
        to: "/projects"
      },
      {
        icon: "mdi-calendar-month-outline",
        text: "SCHEDULE",
        to: "/schedule"
      },
      {
        icon: "mdi-magnify",
        text: "SEARCH",
        to: "/search"
      },
      {
        icon: "mdi-archive",
        text: "ARCHIVE",
        to: "/archive"
      }
    ],
    dialog: false
  }),
  computed: {
    ...mapState({
      // isShowSubMenu: "isShowSubMenu"
    })
  },
  methods: {
    ...mapMutations(["SET_IS_SHOW_SUB_MENU"]),
    // onShowSubMenu(key) {
    //   // console.log(key)
    //   this.SET_IS_SHOW_SUB_MENU({
    //     isShowSubMenu: this.isShowSubMenu,
    //     activeMenu: key
    //   });
    // },
    setActiveMenu(key) {
      // console.log(key)
      this.SET_ACTIVE_MENU(this.menus[key].text);
    },
    logout() {
      this.$store.commit("LOGOUT");
      this.$router.push("/login");
      this.dialog = false;
    }
  }
};
</script>

<style scoped>
#core-navigation-drawer .v-list {
  padding: 0px;
  left: -12px;
  min-height: 56px;
}
#core-navigation-drawer .v-footer-item {
  position: fixed;
  width: 100%;
  bottom: 10px;
}
</style>
