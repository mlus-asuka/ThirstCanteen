package vip.fubuki.thirstcanteen.common.crafting;

import dev.ghen.thirst.content.registry.ThirstComponent;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.NotNull;
import vip.fubuki.thirstcanteen.common.item.Canteen;

public class CanteenSmeltingRecipe extends SmeltingRecipe {


    public CanteenSmeltingRecipe(String groupID, CookingBookCategory cookingBookCategory , Ingredient ingredient, ItemStack result , float experience, int cookingTime) {
        super(groupID,cookingBookCategory ,ingredient, result, experience, cookingTime);
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SingleRecipeInput recipeInput, HolderLookup.@NotNull Provider provider) {
        ItemStack stack = recipeInput.item();
        if(stack.get(ThirstComponent.PURITY)==null){
            if(stack.getItem() instanceof Canteen canteen)
                stack.set(ThirstComponent.PURITY,canteen.getDefaultPurity());
        }
        int purity = Math.min(stack.get(ThirstComponent.PURITY)+1,3);
        ItemStack result = this.result.copy();

        result.set(ThirstComponent.PURITY,purity);

        if(stack.getItem() instanceof Canteen canteen){
            Canteen.setContain(result,canteen.getLeftUsableTimes(stack));
        }
        return result;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return CanteenSmeltingRecipeSerializer.INSTANCE;
    }
}
