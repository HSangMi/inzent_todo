<template>
  <v-navigation-drawer
    id="core-navigation-drawer"
    app
    :dark="true"
    color="rgb(66, 59, 107)"
    :right="$vuetify.rtl"
    mini-variant
    mini-variant-width="56"
    permanent
    v-bind="$attrs"
  >
    <v-divider class="mb-1" />
    <v-list dense nav>
      <v-list-item>
        <v-list-item-avatar class="align-self-center" color="white" contain>
          <v-img
            src="https://demos.creative-tim.com/vuetify-material-dashboard/favicon.ico"
            max-height="30"
          />
        </v-list-item-avatar>

        <v-list-item-content>
          <v-list-item-title class="display-1" />
        </v-list-item-content>
      </v-list-item>
    </v-list>
    <v-divider class="mb-2" />

    <v-list expand>
      <v-list-item-group v-model="activeMenu" color="defualt">
        <v-list-item
          v-for="(menu, i) in menus"
          :key="`menu-${i+1}`"
          :to="menu.to"
          @click="onShowSubMenu(i+1)"
        >
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
              <v-list-item-title></v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-list-item @click.stop="dialog = true">
            <v-list-item-icon>
              <v-icon>mdi-location-exit</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title></v-list-item-title>
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
    menus: [
      {
        icon: "mdi-view-dashboard",
        text: "test",
        to: "/dashboard"
      },
      {
        icon: "mdi-newspaper-variant-multiple-outline",
        text: "test",
        to: "/projects"
      },
      {
        icon: "mdi-calendar-month-outline",
        text: "test",
        to: "/schedule"
      },
      {
        icon: "mdi-magnify",
        text: "test",
        to: "/search"
      },
      {
        icon: "mdi-archive",
        text: "test",
        to: "/archive"
      }
    ],
    dialog: false
  }),
  computed: {
    ...mapState({
      isShowSubMenu: "isShowSubMenu"
    })
  },
  methods: {
    ...mapMutations(["SET_IS_SHOW_SUB_MENU"]),
    onShowSubMenu(key) {
      // console.log(key)
      this.SET_IS_SHOW_SUB_MENU({
        isShowSubMenu: this.isShowSubMenu,
        activeMenu: key
      });
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