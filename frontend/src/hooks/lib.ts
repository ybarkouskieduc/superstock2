import { useSessionStorage } from "./useSessionStorage";
import { useMemo } from "react";

import { useAccountFind } from "./queries";

export const useUserId = () => useSessionStorage("user-id");

export const useUserAccount = (): [
  ReturnType<typeof useAccountFind>,
  number
] => {
  const [userId] = useUserId();

  const account = useAccountFind({
    userId: userId as number,
  });

  const usdAmount = useMemo(
    () =>
      (account.data || []).find(
        ({ currency = "" }) => currency.toLowerCase() === "usd"
      )?.amount || 0,
    [account.data]
  );

  return [account, usdAmount];
};
