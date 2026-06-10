import java.util.*;

class Solution {
    static class RMQ {
        int n;
        int[][] mx;
        int[][] mn;
        int[] lg;

        RMQ(int[] nums) {
            n = nums.length;

            lg = new int[n + 1];
            for (int i = 2; i <= n; i++) {
                lg[i] = lg[i >> 1] + 1;
            }

            int K = lg[n] + 1;

            mx = new int[K][n];
            mn = new int[K][n];

            for (int i = 0; i < n; i++) {
                mx[0][i] = nums[i];
                mn[0][i] = nums[i];
            }

            for (int k = 1; k < K; k++) {
                int len = 1 << k;
                int half = len >> 1;

                for (int i = 0; i + len <= n; i++) {
                    mx[k][i] = Math.max(mx[k - 1][i],
                                        mx[k - 1][i + half]);

                    mn[k][i] = Math.min(mn[k - 1][i],
                                        mn[k - 1][i + half]);
                }
            }
        }

        long value(int l, int r) {
            int len = r - l + 1;
            int k = lg[len];

            int mxVal = Math.max(mx[k][l],
                                 mx[k][r - (1 << k) + 1]);

            int mnVal = Math.min(mn[k][l],
                                 mn[k][r - (1 << k) + 1]);

            return (long) mxVal - mnVal;
        }
    }

    static class Node {
        int l;
        int lo;
        int hi;
        int bestR;
        long val;

        Node(int l, int lo, int hi, int bestR, long val) {
            this.l = l;
            this.lo = lo;
            this.hi = hi;
            this.bestR = bestR;
            this.val = val;
        }
    }

    private RMQ rmq;

    private Node buildNode(int l, int lo, int hi) {
        if (lo > hi) return null;

        int bestR = lo;
        long bestVal = rmq.value(l, lo);

        int left = lo, right = hi;

        while (left <= right) {
            int mid = (left + right) >>> 1;
            long cur = rmq.value(l, mid);

            if (cur >= bestVal) {
                bestVal = cur;
                bestR = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return new Node(l, lo, hi, bestR, bestVal);
    }

    public long maxTotalValue(int[] nums, int k) {
        int n = nums.length;

        rmq = new RMQ(nums);

        PriorityQueue<Node> pq =
            new PriorityQueue<>((a, b) -> Long.compare(b.val, a.val));

        for (int l = 0; l < n; l++) {
            Node node = buildNode(l, l, n - 1);
            if (node != null) {
                pq.offer(node);
            }
        }

        long ans = 0;

        while (k-- > 0 && !pq.isEmpty()) {
            Node cur = pq.poll();

            ans += cur.val;

            Node leftPart =
                buildNode(cur.l, cur.lo, cur.bestR - 1);

            Node rightPart =
                buildNode(cur.l, cur.bestR + 1, cur.hi);

            if (leftPart != null) pq.offer(leftPart);
            if (rightPart != null) pq.offer(rightPart);
        }

        return ans;
    }
}
