

class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int zero = 0;

        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                zero = i;
                break;
            }
        }

        boolean forward = true, backward = true;

        for (int i = 0; i < n; i++) {
            if (nums[(zero + i) % n] != i)
                forward = false;

            if (nums[(zero - i + n) % n] != i)
                backward = false;
        }

        int ans = Integer.MAX_VALUE;

        if (forward) {
            ans = Math.min(ans, zero);
            ans = Math.min(ans, n - zero + 2);
        }

        if (backward) {
            ans = Math.min(ans, zero + 2);
            ans = Math.min(ans, n - zero);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}
