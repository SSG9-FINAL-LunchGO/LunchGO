import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useRestaurantStore = defineStore('restaurant', () => {
  // State
  const restaurantId = ref(null); // 현재 편집 중인 식당 ID
  const menus = ref([]);

  // Actions
  function loadRestaurant(data) {
    console.log('[Store] loadRestaurant called. Current restaurantId:', restaurantId.value, 'New ID:', data.restaurantId);
    if (restaurantId.value === data.restaurantId) {
      console.log('[Store] Same restaurant, not overwriting menus.');
      return;
    }
    console.log('[Store] Loading new restaurant data.');
    restaurantId.value = data.restaurantId;
    menus.value = [...(data.menus || [])];
    console.log('[Store] Menus after load:', menus.value);
  }

  function clearRestaurant() {
    console.log('[Store] clearRestaurant called.');
    restaurantId.value = null;
    menus.value = [];
  }

  function setMenus(newMenus) {
    console.log('[Store] setMenus called.');
    menus.value = [...newMenus];
  }

  function addMenu(menu) {
    console.log('[Store] addMenu called with:', menu);
    console.log('[Store] Menus before add:', menus.value);
    menus.value.push(menu);
    console.log('[Store] Menus after add:', menus.value);
  }

  function updateMenu(updatedMenu) {
    const index = menus.value.findIndex(menu => menu.id === updatedMenu.id);
    if (index !== -1) {
      menus.value[index] = updatedMenu;
    }
  }

  function deleteMenu(menuId) {
    menus.value = menus.value.filter(menu => menu.id !== menuId);
  }

  function getMenuById(menuId) {
    const id = Number(menuId);
    return menus.value.find(menu => menu.id === id);
  }
  
  function getNextId() {
    return Date.now();
  }

  return { 
    restaurantId,
    menus, 
    loadRestaurant,
    clearRestaurant,
    setMenus,
    addMenu, 
    updateMenu, 
    deleteMenu,
    getMenuById,
    getNextId,
  };
});
