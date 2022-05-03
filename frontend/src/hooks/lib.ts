import useLocalStorageState from "use-local-storage-state";
import { useMemo } from "react";

import { useAccountFind } from "./queries";

export const useUserId = () =>
  useLocalStorageState<null | number>("user-id", {
    defaultValue: null,
  });

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
