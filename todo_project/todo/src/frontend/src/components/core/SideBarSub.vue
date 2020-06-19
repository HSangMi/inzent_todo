<template>
    <v-navigation-drawer
        id="core-navigation-drawer-sub"
        :right="$vuetify.rtl"
        mobile-breakpoint="435"
        color="#FFFFFF"
        app
        width="250"
        v-bind="$attrs"
    >
			<v-list
				expand
			>
			<!-- <v-list-item @click.prevent="" class="sidebar-menu-header">
							<v-list-item-title>{{menuTitle[activeMenu]}}</v-list-item-title>
					</v-list-item> -->
			<v-list-item-group v-model="activeSub" color="defualt">

				<v-list-item
					v-for="(menu, i) in menus[activeMenu].items"
					:key="`menu-${i}`"
					:to="menu.to"
				>
					<!-- <v-list-item-icon>
						<v-icon></v-icon>
					</v-list-item-icon> -->
					<v-list-item-content>
						<v-list-item-title>{{menu.title}}</v-list-item-title>
					</v-list-item-content>
				</v-list-item>
			</v-list-item-group>
			</v-list>

		<template v-slot:append>
      <base-item
        :item="{
          title: 'upgrade',
          icon: 'mdi-package-up',
          to: '/upgrade',
        }"
      />
    </template>
			
    </v-navigation-drawer>
</template>

<script>
import {mapState} from 'vuex'

export default {
		data: () => ({
			name: 'SidebarSub',
			activeSub : undefined,
			menuTitle : ['USER NAME','PROJECT','CALENDAR','GANTT CHART','TASK REPORTS','ORG CHART','NOTICE MSG'],
      menus: [
				{ items: [{title: 'MY TASK', to: '/user/myTask'}, {title: 'PROFILE', to: '/user/profile'},{title: 'ORGCHART', to: '/user/orgChart'}]},
				{ items: [{title: 'HOME', to: '/projects/home'},]},
				{ items: []},
				{ items: [{title: '', to: ''}]},
				{ items: [{title: 'ALL', to: '/projects/all'}, {title: 'ARCIVE', to: '/todayTask'},{title: '관심업무', to: '/staredTask'}]},
				{ items: [{title: 'ALL', to: '/projects/all'}, {title: 'ARCIVE', to: '/todayTask'},{title: '관심업무', to: '/staredTask'}]},
			],
		}),
	computed : {
		...mapState({
				activeMenu: 'activeMenu',
			})
		
	},
   

}
</script>

<style>
#core-navigation-drawer-sub .v-list {
	margin-left: 56px;
	padding: 0px;
}
#core-navigation-drawer-sub .v-list-item {
	min-height: 56px
}
#core-navigation-drawer-sub .sidebar-menu-header{
	background-color:rgba(66, 59, 107, 0.55);
}
</style>