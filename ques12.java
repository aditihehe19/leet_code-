class Solution {
    public long maxTotalValue(int[] nums, int k) {
        int mn = Integer.MAX_VALUE;
        int mx = Integer.MIN_VALUE;

        for(int num : nums) {
            mn = Math.min(mn, num);
            mx = Math.max(mx, num);
        }

        return 1L * k * (mx - mn);
    }
}
