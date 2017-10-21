package com.github.the_only_true_bob.the_bob.matcher;

import com.github.the_only_true_bob.the_bob.vk.User;

public class CommunicativePair {
    private final User left;
    private final User right;

    public CommunicativePair(final User userA, final User userB) {
        if (userA.vkId().compareTo(userB.vkId()) < 0) {
            this.left = userA;
            this.right = userB;
        } else {
            this.left = userB;
            this.right = userA;
        }
    }

    public static CommunicativePair of(final User userA, final User userB) {
        return new CommunicativePair(userA, userB);
    }

    public User left() {
        return left;
    }

    public User right() {
        return right;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final CommunicativePair that = (CommunicativePair) o;

        if (left != null ? !left.equals(that.left) : that.left != null) return false;
        return right != null ? right.equals(that.right) : that.right == null;
    }

    @Override
    public int hashCode() {
        int result = left != null ? left.hashCode() : 0;
        result = 31 * result + (right != null ? right.hashCode() : 0);
        return result;
    }
}