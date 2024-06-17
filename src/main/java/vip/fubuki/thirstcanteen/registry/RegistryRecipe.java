package vip.fubuki.thirstcanteen.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;
import vip.fubuki.thirstcanteen.ThirstCanteen;
import vip.fubuki.thirstcanteen.common.crafting.CanteenCampfireRecipeSerializer;
import vip.fubuki.thirstcanteen.common.crafting.CanteenSmeltingRecipeSerializer;

public class RegistryRecipe {
    public static DeferredRegister<RecipeSerializer<?>> recipeRegister = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER,ThirstCanteen.MODID);

    static{
        recipeRegister.register("canteen_smelting",()-> CanteenSmeltingRecipeSerializer.INSTANCE);
        recipeRegister.register("canteen_campfire_cooking",()-> CanteenCampfireRecipeSerializer.INSTANCE);
    }
}
