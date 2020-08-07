<template>
  <v-app-bar id="app-bar" absolute app color="transparent" flat height="50">
    <v-toolbar-title
      class="hidden-sm-and-down font-weight-light"
      v-text="headerTitle"
    />
    <v-spacer />

    <!-- <v-text-field
      v-model="search"
      append-icon="mdi-magnify"
      label="Search"
      style="max-width: 165px;"
      single-line
      hide-details
    ></v-text-field>
    <div class="mx-1" /> -->

    <!-- <v-btn
      class="ml-2"
      min-width="0"
      text
      to="/"
    >
      <v-icon>mdi-view-dashboard</v-icon>
    </v-btn>-->
    <v-menu
      bottom
      left
      offset-y
      origin="top right"
      transition="scale-transition"
      :value="userInfo"
    >
      <!-- <v-img
            src="https://demos.creative-tim.com/vuetify-material-dashboard/favicon.ico"
            max-height="20"
          /> -->
      <!-- <v-img :src="this.user.imgCode" max-height="20" /> -->
      <template v-slot:activator="{ attrs, on }">
        <v-btn
          depressed
          outline
          class="mt-2"
          min-width="0"
          color="white"
          v-bind="attrs"
          v-on="on"
        >
          <v-avatar size="30px">
            <img :src="userInfo.imgCode" />
          </v-avatar>
          <v-card-text>{{ userInfo.id }}</v-card-text>
        </v-btn>
      </template>
      <!-- <template v-slot:activator="{ attrs, on }">
        <v-btn class="ml-2" min-width="0" text v-bind="attrs" v-on="on">
          <v-badge color="red" overlap bordered>
            <template v-slot:badge>
              <span>5</span>
            </template>

          <v-icon color="grey">mdi-bell</v-icon>
          </v-badge>
        </v-btn>
      </template> -->

      <v-list :tile="false" nav>
        <div>
          <app-bar-item v-for="(n, i) in notifications" :key="`item-${i}`">
            <v-list-item-title v-text="n" />
          </app-bar-item>
        </div>
      </v-list>
    </v-menu>
    <!-- <v-menu bottom left offset-y origin="top right" transition="scale-transition">

      <v-list :tile="false" nav>
        <div>
          <app-bar-item v-for="(n, i) in userHeaderMenu" :key="`item-${i}`">
            <v-list-item-title v-text="n" />
          </app-bar-item>
        </div>
      </v-list>
    </v-menu>-->
  </v-app-bar>
</template>

<script>
// Components
import { VHover, VListItem } from "vuetify/lib";

// Utilities
import { mapState, mapMutations } from "vuex";

export default {
  name: "DashboardCoreAppBar",

  components: {
    AppBarItem: {
      render(h) {
        return h(VHover, {
          scopedSlots: {
            default: ({ hover }) => {
              return h(
                VListItem,
                {
                  attrs: this.$attrs,
                  class: {
                    "black--text": !hover,
                    "white--text secondary": hover,
                  },
                  props: {
                    activeClass: "",
                    dark: hover,
                    link: true,
                    ...this.$attrs,
                  },
                },
                this.$slots.default
              );
            },
          },
        });
      },
    },
  },

  props: {
    value: {
      type: Boolean,
      default: false,
    },
  },

  data: () => ({
    notifications: ["내 정보", "로그아웃"],
    userHeaderMenu: ["user profile", "logout"],
    search: "",
  }),
  created() {},
  computed: {
    ...mapState({
      activeMenu: "activeMenu",
      headerTitle: "headerTitle",
      userInfo: "userInfo",
    }),
  },

  methods: {
    ...mapMutations({
      setDrawerSub: "SET_DRAWER_SUB",
    }),
  },
};
</script>
<style scoped>
.v-toolbar__title {
  font-weight: 600 !important;
  color: dimgrey;
}
</style>
