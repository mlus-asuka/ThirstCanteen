package vip.fubuki.thirstcanteen.common.crafting;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class CanteenCampfireRecipe extends CampfireCookingRecipe {
    public CanteenCampfireRecipe(ResourceLocation recipeType, String groupID, Ingredient ingredient, ItemStack result , float experience, int cookingTime) {
        super(recipeType, groupID, ingredient, result, experience, cookingTime);
    }

    public CanteenCampfireRecipe(CampfireCookingRecipe recipe){
        super(recipe.getId(),recipe.getGroup(),recipe.getIngredients().get(0), recipe.getResultItem(),recipe.getExperience(),recipe.getCookingTime());
    }

    @Override
    @NotNull
    public ItemStack assemble(Container container) {
        int purity;
        int contain;

        ItemStack stack = container.getItem(0);
        CompoundTag compoundTag = stack.getOrCreateTag();
        purity = Math.min(compoundTag.getInt("Purity")+1,3);
        contain = compoundTag.getInt("Contain");

        ItemStack result = getResultItem().copy();
        CompoundTag tag = result.getOrCreateTag();
        tag.putInt("Purity",purity);
        tag.putInt("Contain",contain);

        return result;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return CanteenCampfireRecipeSerializer.INSTANCE;
    }
}
