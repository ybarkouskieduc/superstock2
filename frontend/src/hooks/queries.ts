import axios from "axios";
import { useMutation, useQueries, useQuery, useQueryClient } from "react-query";
import { toIsoString } from "../helpers/dateHelper";

const http = axios.create({ baseURL: "http://localhost:8082/api/v1" });

export const QUERY_KEYS = {
  user: "user",
  userFind: "userFind",
  userProfitHalfOfHour: "userProfitHalfOfHour",
  userProfitHour: "userProfitHour",
  userProfitDay: "userProfitDay",
  userProfitWeek: "userProfitWeek",

  account: "account",
  accountFind: "accountFind",

  stock: "stock",
  stockFind: "stockFind",
  stockAccountFind: "stockAccountFind",
  stockTransactionFind: "stockTransactionFind",
  stockPending: "stockPending",

  bankExchangeFind: "bankExchangeFind",
  bankExchangeTransactionFind: "bankExchangeTransactionFind",
  bankExchange: "bankExchange",
  bankFind: "bankFind",
  bankReviewFind: "bankReviewFind",
  bank: "bank",
};

const apiUserUpdate = (data: {
  userId?: number /** Format: int64 */;
  username?: string;
  password?: string;
}) =>
  http
    .put<{
      id?: number /** Format: int64 */;
      username?: string;
      password?: string;
    }>("user/update", data)
    .then(({ data }) => data);
export const useUserUpdate = () => {
  const qc = useQueryClient();
  return useMutation(apiUserUpdate);
};

const apiBankReviewUpdate = () =>
  http
    .put<{
      id?: number /** Format: int64 */;
      userId?: number /** Format: int64 */;
      bankId?: number /** Format: int64 */;
      rate?: number /** Format: int32 */;
      review?: string;
      createdAt?: string /** Format: date-time */;
    }>("bank/review/update")
    .then(({ data }) => data);

export const useBankReviewUpdate = () => {
  const qc = useQueryClient();
  return useMutation(apiBankReviewUpdate);
};

const apiAccountUpdate = () =>
  http
    .put<{
      id?: number /** Format: int64 */;
      userId?: number /** Format: int64 */;
      currency?: string;
      amount?: number;
      default?: boolean;
    }>("account/update")
    .then(({ data }) => data);
export const useAccountUpdate = () => {
  const qc = useQueryClient();
  return useMutation(apiAccountUpdate);
};

const apiUserCreate = (data: { username?: string; password?: string }) =>
  http
    .post<{
      id?: number /** Format: int64 */;
      username?: string;
      password?: string;
    }>("user/create", data)
    .then(({ data }) => data);
export const useUserCreate = () => {
  const qc = useQueryClient();
  return useMutation(apiUserCreate);
};

const apiStockCreate = (data: {
  name?: string;
  sign?: string;
  description?: string;
}) =>
  http
    .post<{
      id?: number /** Format: int64 */;
      name?: string;
      sign?: string;
      description?: string;
    }>("stock/create", data)
    .then(({ data }) => data);
export const useStockCreate = () => {
  const qc = useQueryClient();
  return useMutation(apiStockCreate);
};

const apiStockAccountCreate = (data: {
  userId?: number /** Format: int64 */;
  stockId?: number /** Format: int64 */;
  amount?: number;
}) =>
  http
    .post<{
      id?: number /** Format: int64 */;
      stockId?: number /** Format: int64 */;
      userId?: number /** Format: int64 */;
      amount?: number;
    }>("stock/account/create", data)
    .then(({ data }) => data);
export const useStockAccountCreate = () => {
  const qc = useQueryClient();
  return useMutation(apiStockAccountCreate);
};

const apiBankReviewCreate = () =>
  http
    .post<{
      id?: number /** Format: int64 */;
      userId?: number /** Format: int64 */;
      bankId?: number /** Format: int64 */;
      rate?: number /** Format: int32 */;
      review?: string;
      createdAt?: string /** Format: date-time */;
    }>("bank/review/create")
    .then(({ data }) => data);
export const useBankReviewCreate = () => {
  const qc = useQueryClient();
  return useMutation(apiBankReviewCreate);
};

const apiBankExchangeCreate = () =>
  http
    .post<{
      id?: number /** Format: int64 */;
      bankId?: number /** Format: int64 */;
      currencyIn?: string;
      currencyOut?: string;
      rate?: number;
      fee?: number;
    }>("bank/exchange/create")
    .then(({ data }) => data);
export const useBankExchangeCreate = () => {
  const qc = useQueryClient();
  return useMutation(apiBankExchangeCreate);
};

const apiBankCreate = () =>
  http
    .post<{
      id?: number /** Format: int64 */;
      name?: string;
      description?: string;
      country?: string;
      createdAt?: string /** Format: date-time */;
    }>("bank/create")
    .then(({ data }) => data);
export const useBankCreate = () => {
  const qc = useQueryClient();
  return useMutation(apiBankCreate);
};

const apiAccountCreate = (data: {
  userId: number;
  currency: string;
  amount: number;
  isDefault: boolean;
}) =>
  http
    .post<{
      id?: number /** Format: int64 */;
      userId?: number /** Format: int64 */;
      currency?: string;
      amount?: number;
      default?: boolean;
    }>("account/create", data)
    .then(({ data }) => data);

export const useAccountCreate = () => {
  const qc = useQueryClient();
  return useMutation(apiAccountCreate, {
      onSuccess: () => {
          qc.invalidateQueries()
      }
  });
};

const apiUser = () => () =>
  http
    .get<
      {
        id?: number /** Format: int64 */;
        username?: string;
        password?: string;
      }[]
    >("user")
    .then(({ data }) => data);
export const useUser = () => useQuery(QUERY_KEYS.user, apiUser());

export const useUserLogin = () =>
  useMutation((data: { username?: string; password?: string }) =>
    http
      .post<{
        id?: number /** Format: int64 */;
        username?: string;
        password?: string;
      }>("user/login", data)
      .then(({ data }) => data)
  );

export const useUserFind = (params: {
  userId?: number /** Format: int64 */;
  username?: string;
  findAllOnEmptyCriteria?: boolean;
}) =>
  useQuery(QUERY_KEYS.userFind, () =>
    http
      .get<{
        id?: number /** Format: int64 */;
        username?: string;
        password?: string;
      }>("user/find", { params })
      .then(({ data }) => data)
  );

const apiStock = () => () =>
  http
    .get<
      {
        id?: number /** Format: int64 */;
        name?: string;
        sign?: string;
        description?: string;
        currentStockPrice?: number;
      }[]
    >("stock")
    .then(({ data }) => data);
export const useStock = () => useQuery(QUERY_KEYS.stock, apiStock());

export const useStockTransactionFind = (params: {
  id?: number /** Format: int64 */;
  userId?: number /** Format: int64 */;
  stockId?: number /** Format: int64 */;
  stockPriceId?: number /** Format: int64 */;
  stockPrice?: number /** Format: int64 */;
  goal?: "SELL" | "BUY" /** @enum {string} */;
}) =>
  useQuery(QUERY_KEYS.stockTransactionFind, () =>
    http
      .get<
        {
          id?: number /** Format: int64 */;
          stockId?: number /** Format: int64 */;
          userId?: number /** Format: int64 */;
          stockPriceId?: number /** Format: int64 */;
          stockPrice?: number /** Format: int64 */;
          amount?: number;
          createdAt?: string /** Format: date-time */;
          revertedAt?: string /** Format: date-time */;
          goal?: "SELL" | "BUY" /** @enum {string} */;
        }[]
      >("stock/transaction/find", { params })
      .then(({ data }) => data)
  );

export const useStockPending = (params: {
  userId?: number /** Format: int64 */;
}) =>
  useQuery(QUERY_KEYS.stockPending, () =>
    http
      .get<
        {
          id: number;
          userId: number;
          stockId: number;
          amount: number;
          desiredPrice: number;
          createdAt: string;
          executedAt: string;
        }[]
      >("stock/exchange/request/pending", { params })
      .then(({ data }) => data)
  );

export const useStockFind = (params: {
  id?: number /** Format: int64 */;
  name?: string;
  sign?: string;
  findAllOnEmptyCriteria?: boolean;
}) =>
  useQuery(QUERY_KEYS.stockFind, () =>
    http
      .get<{
        id?: number /** Format: int64 */;
        name?: string;
        sign?: string;
        description?: string;
      }>("stock/find", { params })
      .then(({ data }) => data)
  );

export const useStockPriceFind = (params: {
  stockId?: number /** Format: int64 */;
  lastStockPrice?: boolean;
}) =>
  useQuery([QUERY_KEYS.stockFind, params], () =>
    http
      .get<
        {
          id?: number /** Format: int64 */;
          stockId: number;
          price: number;
          createdAt: string;
        }[]
      >("stock/price/find", { params })
      .then(({ data }) => data)
  );

export const useStockPriceProfit = (params: {
    stockId?: number /** Format: int64 */;
    userId?: number;
    dateFrom?: string;
    dateTo?: string;
}) => {
    const timePeriods = {
        halfOfHour(): string {
            const date = new Date();
            date.setMinutes(date.getMinutes() - 30);
            return toIsoString(date);
        },
        hour(): string {
            const date = new Date();
            date.setHours(date.getHours() - 1);
            return toIsoString(date);
        },
        day(): string {
            const date = new Date();
            date.setHours(date.getHours() - 24);
            return toIsoString(date);
        },
        week(): string {
            const date = new Date();
            date.setHours(date.getHours() - (24 * 7));
            return toIsoString(date);
        }
    }

    const queryFn = (dateFrom: string) => {
        params.dateFrom = dateFrom;
        params.dateTo = toIsoString(new Date());
        return http.get<
            {
                stockName?: string,
                stockId?: number,
                profit: number
            }
            >("user/flow/profit/stock", { params })
            .then(({ data }) => data.profit)
    }

    return useQueries([
        {
            queryKey: [QUERY_KEYS.userProfitHalfOfHour, params],
            queryFn() {
                return queryFn(timePeriods.halfOfHour());
            }
        },
        {
            queryKey: [QUERY_KEYS.userProfitHour, params],
            queryFn() {
                return queryFn(timePeriods.hour());
            }
        },
        {
            queryKey: [QUERY_KEYS.userProfitDay, params],
            queryFn() {
                return queryFn(timePeriods.day());
            }
        },
        {
            queryKey: [QUERY_KEYS.userProfitWeek, params],
            queryFn() {
                return queryFn(timePeriods.week());
            }
        }
    ]);
}

export const useStockAccountFind = (params: {
  id?: number /** Format: int64 */;
  userId?: number /** Format: int64 */;
  stockId?: number /** Format: int64 */;
}) =>
  useQuery([QUERY_KEYS.stockAccountFind, params], () =>
    http
      .get<
        {
          id?: number /** Format: int64 */;
          stockId?: number /** Format: int64 */;
          userId?: number /** Format: int64 */;
          amount?: number;
        }[]
      >("stock/account/find", { params })
      .then(({ data }) => data.filter(({ amount }) => !!amount))
  );

const apiBank = () => () =>
  http
    .get<
      {
        id?: number /** Format: int64 */;
        name?: string;
        description?: string;
        country?: string;
        createdAt?: string /** Format: date-time */;
      }[]
    >("bank")
    .then(({ data }) => data);

export const useBank = () => useQuery(QUERY_KEYS.bank, apiBank());

export const useBankReviewFind = (params: {
  reviewId?: number /** Format: int64 */;
  userId?: number /** Format: int64 */;
  bankId?: number /** Format: int64 */;
}) =>
  useQuery([QUERY_KEYS.bankReviewFind, params], () =>
    http
      .get<
        {
          id?: number /** Format: int64 */;
          userId?: number /** Format: int64 */;
          bankId?: number /** Format: int64 */;
          rate?: number /** Format: int32 */;
          review?: string;
          createdAt?: string /** Format: date-time */;
        }[]
      >("bank/review/find", { params })
      .then(({ data }) => data)
  );

export const useCreteBankReview = () => {
  const qc = useQueryClient();
  return useMutation(
    (data: { bankId: number; userId: number; rate: number; review: string }) =>
      http.post("bank/review/create", data).then(({ data }) => data),
    {
      onSuccess: () => {
        qc.invalidateQueries();
      },
    }
  );
};

export const useBankFind = (params: {
  id?: number /** Format: int64 */;
  name?: string;
  findAllOnEmptyCriteria?: boolean;
}) =>
  useQuery(QUERY_KEYS.bankFind, () =>
    http
      .get<
        {
          id?: number /** Format: int64 */;
          name?: string;
          description?: string;
          country?: string;
          createdAt?: string /** Format: date-time */;
        }[]
      >("bank/find", { params })
      .then(({ data }) => data)
  );

const apiBankExchange = () => () =>
  http
    .get<
      {
        id?: number /** Format: int64 */;
        bankId?: number /** Format: int64 */;
        currencyIn?: string;
        currencyOut?: string;
        rate?: number;
        fee?: number;
      }[]
    >("bank/exchange")
    .then(({ data }) => data);

export const useBankExchange = () =>
  useQuery(QUERY_KEYS.bankExchange, apiBankExchange());

export const useBankExchangeTransactionFind = (params: {
  id?: number /** Format: int64 */;
  bankId?: number /** Format: int64 */;
  userId?: number /** Format: int64 */;
  currencyIn?: "USD" | "BYN" | "RUB" | "EUR" | string /** @enum {string} */;
  currencyOut?: "USD" | "BYN" | "RUB" | "EUR" | string /** @enum {string} */;
  rate?: number;
  fee?: number;
}) =>
  useQuery(QUERY_KEYS.bankExchangeTransactionFind, () =>
    http
      .get<
        {
          id?: number /** Format: int64 */;
          userId?: number /** Format: int64 */;
          bankId?: number /** Format: int64 */;
          currencyIn?: string;
          currencyOut?: string;
          amountIn?: number;
          amountOut?: number;
          rate?: number;
          fee?: number;
          createdAt?: string /** Format: date-time */;
          updatedAt?: string /** Format: date-time */;
        }[]
      >("bank/exchange/transaction/find", { params })
      .then(({ data }) => data)
  );

export const useBankExchangeFind = (params: {
  id?: number /** Format: int64 */;
  bankId?: number /** Format: int64 */;
  currencyIn?: "USD" | "BYN" | "RUB" | "EUR" | string /** @enum {string} */;
  currencyOut?: "USD" | "BYN" | "RUB" | "EUR" | string /** @enum {string} */;
  rate?: number;
  fee?: number;
  lastExchangeRate?: boolean;
  findAllOnEmptyCriteria?: boolean;
}) =>
  useQuery(QUERY_KEYS.bankExchangeFind, () =>
    http
      .get<
        {
          id?: number /** Format: int64 */;
          bankId?: number /** Format: int64 */;
          currencyIn?: string;
          currencyOut?: string;
          rate?: number;
          fee?: number;
        }[]
      >("bank/exchange/find", { params })
      .then(({ data }) => data)
  );

const apiAccount = () => () =>
  http
    .get<
      {
        id?: number /** Format: int64 */;
        userId?: number /** Format: int64 */;
        currency?: string;
        amount?: number;
        default?: boolean;
      }[]
    >("account")
    .then(({ data }) => data);
export const useAccount = () => useQuery(QUERY_KEYS.account, apiAccount());

export const useAccountFind = (params: {
  id?: number /** Format: int64 */;
  userId?: number /** Format: int64 */;
  currency?: "USD" | "BYN" | "RUB" | "EUR" | string /** @enum {string} */;
  findAllOnEmptyCriteria?: boolean;
}) =>
  useQuery(QUERY_KEYS.accountFind, () =>
    http
      .get<
        {
          id?: number /** Format: int64 */;
          userId?: number /** Format: int64 */;
          currency?: string;
          amount?: number;
          default?: boolean;
        }[]
      >("account/find", { params })
      .then(({ data }) => data)
  );

export const useUserSellStock = () => {
  const qc = useQueryClient();
  return useMutation(
    (data: {
      userId: number;
      stockId: number;
      amount: number;
      desiredPriceInUSD?: number;
    }) => http.post("user/flow/sell/stock", data).then(({ data }) => data),
    {
      onSuccess: () => {
        qc.invalidateQueries();
      },
    }
  );
};

export const useUserBuyStock = () => {
  const qc = useQueryClient();
  return useMutation(
    (data: {
      userId: number;
      stockId: number;
      amount: number;
      desiredPriceInUSD?: number;
    }) => http.post("user/flow/buy/stock", data).then(({ data }) => data),
    {
      onSuccess: () => {
        qc.invalidateQueries();
      },
    }
  );
};

export const useUserBuyStockSchedule = () => {
  const qc = useQueryClient();
  return useMutation(
    (data: {
      userId: number;
      stockId: number;
      amount: number;
      desiredPriceInUSD?: number;
    }) =>
      http.post("user/flow/buy/stock/schedule", data).then(({ data }) => data),
    {
      onSuccess: () => {
        qc.invalidateQueries();
      },
    }
  );
};

export const useUserExchangeCurrency = () => {
  const qc = useQueryClient();
  return useMutation(
    (data: {
      userId: number;
      bankId: number;
      currencyIn: "USD" | "BYN" | "RUB" | "EUR" | string /** @enum {string} */;
      currencyOut: "USD" | "BYN" | "RUB" | "EUR" | string /** @enum {string} */;
      amount: number;
    }) =>
      http.post("user/flow/exchange/currency", data).then(({ data }) => data),
    {
      onSuccess: () => {
        qc.invalidateQueries();
      }
    }
  );
};
