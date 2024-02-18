package vip.fubuki.thirstcanteen.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import vip.fubuki.thirstcanteen.ThirstCanteen;
import vip.fubuki.thirstcanteen.common.crafting.CanteenCampfireRecipe;
import vip.fubuki.thirstcanteen.common.crafting.CanteenCampfireRecipeSerializer;
import vip.fubuki.thirstcanteen.common.crafting.CanteenSmeltingRecipe;
import vip.fubuki.thirstcanteen.common.crafting.CanteenSmeltingRecipeSerializer;

public class RegistryRecipe {
    public static final ResourceLocation CANTEEN_SMELTING = ThirstCanteen.location("canteen_smelting");
    public static final ResourceLocation CANTEEN_CAMPFIRE = ThirstCanteen.location("canteen_campfire_cooking");

    public static final RegistryObject<RecipeSerializer<CanteenSmeltingRecipe>> CANTEEN_SMELTING_RECIPE = RegistryObject.create(CANTEEN_SMELTING, ForgeRegistries.RECIPE_SERIALIZERS);
    public static final RegistryObject<RecipeSerializer<CanteenCampfireRecipe>> CANTEEN_CAMPFIRE_RECIPE = RegistryObject.create(CANTEEN_CAMPFIRE, ForgeRegistries.RECIPE_SERIALIZERS);

    public static void register(RegisterEvent.RegisterHelper<RecipeSerializer<?>> helper) {
        helper.register(CANTEEN_SMELTING, CanteenSmeltingRecipeSerializer.INSTANCE);
        helper.register(CANTEEN_CAMPFIRE, CanteenCampfireRecipeSerializer.INSTANCE);
    }
}
