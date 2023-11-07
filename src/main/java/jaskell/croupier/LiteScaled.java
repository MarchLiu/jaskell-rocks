package jaskell.croupier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2023/03/28 18:53
 */
public class LiteScaled<T> implements Poker<T> {
    private final Scale<T> scale;

    private final Random random;

    public LiteScaled(Scale<T> scale, Random random) {
        this.scale = scale;
        this.random = random;
    }

    @Override
    public Optional<Integer> select(List<? extends T> cards) {
        if (cards == null || cards.isEmpty()) {
            return Optional.empty();
        }
        if (cards.size() == 1) {
            return Optional.of(0);
        }

        List<Integer> steps = new ArrayList<>();
        for (int idx=0; idx < cards.size(); idx ++) {
            int weight = scale.weight(cards.get(idx));
            for(int i=0; i < weight; i++){
                steps.add(idx);
            }
        }

        int score = random.nextInt(steps.size());

        return Optional.of(steps.get(score));
    }
}
