package vip.fubuki.thirstcanteen.common.crafting;

import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class CanteenCampfireRecipe extends CampfireCookingRecipe {

    public CanteenCampfireRecipe(ResourceLocation id, String groupID, CookingBookCategory cookingBookCategory, Ingredient ingredient, ItemStack result , float experience, int cookingTime) {
        super(id, groupID, cookingBookCategory, ingredient ,result, experience, cookingTime);
    }

    public CanteenCampfireRecipe(CampfireCookingRecipe recipe){
        super(recipe.getId(),recipe.getGroup(),recipe.category(),recipe.getIngredients().get(0), recipe.getResultItem(RegistryAccess.EMPTY),recipe.getExperience(),recipe.getCookingTime());
    }

    @Override
    @NotNull
    public ItemStack assemble(Container container, @NotNull RegistryAccess registryAccess) {
        int purity;
        int contain;

        ItemStack stack = container.getItem(0);
        CompoundTag compoundTag = stack.getOrCreateTag();
        purity = Math.min(compoundTag.getInt("Purity")+1,3);
        contain = compoundTag.getInt("Contain");

        ItemStack result = getResultItem(registryAccess).copy();
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
