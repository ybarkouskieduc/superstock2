import React, { useMemo, useState } from "react";
import {AxiosError } from "axios";
import {
  Box,
  Button,
  Chip,
  FormControl,
  Grid,
  InputLabel,
  MenuItem,
  Modal,
  Rating,
  Select,
  TextField,
  Typography,
} from "@mui/material";

import { useUserAccount, useUserId } from "../hooks/lib";
import {
  useAccountCreate,
  useAccountFind,
  useBank,
  useBankExchange,
  useBankReviewFind,
  useCreteBankReview,
  useUserExchangeCurrency,
} from "../hooks/queries";

const useFreeCurrencies = () => {
  const [userId] = useUserId();

  const currencies = useMemo(() => ["USD", "BYN", "EUR", "RUB"], []);

  const { data: accounts = [] } = useAccountFind({
    userId: userId as number,
  });

  return currencies.filter(
    (currency) => !accounts.map(({ currency }) => currency).includes(currency)
  );
};

const CreateBankReview: React.FC<{ bankId?: number }> = ({ bankId }) => {
  const [open, setOpen] = useState(false);
  const [userId] = useUserId();

  const { mutate: createReview } = useCreteBankReview();

  const [rate, setRate] = useState(4);
  const [review, setReview] = useState("");

  const handleOpen = () => setOpen(true);
  const handleClose = () => {
    setOpen(false);
    setRate(4);
    setReview("");
  };
  const handleCreateReview = (bankId: number) => {
    createReview({
      userId: userId as number,
      bankId,
      rate,
      review,
    })
    handleClose();
  }

  if (!bankId) return null;

  return (
    <>
      <Chip label="Оставить отзыв" onClick={handleOpen} />
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Typography variant="h5" sx={{ textAlign: "center" }}>
            Отзывы
          </Typography>
          <Rating
            sx={{ mb: 2 }}
            value={rate}
            onChange={(_, rate) => !!rate && setRate(rate)}
          />
          <TextField
            sx={{ mb: 2 }}
            label="Отзыв"
            fullWidth
            multiline
            rows={3}
            value={review}
            onChange={(e) => setReview(e.target.value)}
          />
          <Button
            fullWidth
            disabled={!review || !rate}
            onClick={() => handleCreateReview(bankId)}
          >
            Create
          </Button>
        </Box>
      </Modal>
    </>
  );
};

const CreateBankAccount: React.FC = () => {
  const [open, setOpen] = useState(false);

  const handleOpen = () => setOpen(true);
  const handleClose = () => {
    setOpen(false);
    setSelectedAccount("");
    setAmount(0);
  };

  const [selectedAccount, setSelectedAccount] = useState("");
  const [amount, setAmount] = useState<number | string>(0);
  const [error, setError] = useState('');

  const [userId] = useUserId();

  const freeAccounts = useFreeCurrencies();

  const { mutate: createAccount } = useAccountCreate();
  const handleCreateAccount = (): void => {
    createAccount(
        {
          userId: userId as number,
          currency: selectedAccount,
          amount: +amount,
          isDefault: false,
        },
        {
          onSuccess: () => {
            setOpen(false)
          },
          onError: (error) => {
            if (typeof (error as AxiosError).response?.data !== "string") {
              setError('Проблемы на сервере')
            } else {
              setError((error as AxiosError).response?.data)
            }
          }
        }
    )
  }

  if (!freeAccounts.length) return null;

  return (
    <>
      <Chip label="Создать счет" onClick={handleOpen} />
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Typography variant="h5" sx={{ textAlign: "center", mb: 2 }}>
            Create account
          </Typography>
          <FormControl sx={{ mb: 2 }} fullWidth>
            <InputLabel id="demo-simple-select-label">Currency</InputLabel>
            <Select
              labelId="demo-simple-select-label"
              id="demo-simple-select"
              label="Currency"
              value={selectedAccount}
              onChange={(e) => setSelectedAccount(e.target.value)}
            >
              {freeAccounts.map((currency) => (
                <MenuItem key={currency} value={currency}>
                  {currency}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
          <TextField
            sx={{ mb: 2 }}
            label="Amount"
            type="number"
            fullWidth
            value={amount}
            onChange={(e) => setAmount(e.target.value ? Math.abs(+e.target.value) : '')}
          />
          <Button
            fullWidth
            disabled={!amount || !selectedAccount}
            onClick={handleCreateAccount}
          >
            Create
          </Button>
          <Typography sx={{ textAlign: "center", color: "#e50707" }}>{error && error}</Typography>
        </Box>
      </Modal>
    </>
  );
};

const BankAccount: React.FC = () => {
  const [fromCurrency, setFromCurrency] = useState("");
  const [toCurrency, setToCurrency] = useState("");
  const [exchangeAmount, setExchangeAmount] = useState<number | string>(0);
  const [exchangeBank, setExchangeBank] = useState(0);

  const [error, setError] = useState('');
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => {
    setOpen(false);
    // setToCurrency("");
    // setFromCurrency("");
    setExchangeAmount(0);
    setExchangeBank(0);
  };

  const [{ data: bankAccount = [] }, usdAmount] = useUserAccount();
  const [userId] = useUserId();
  const { mutate: exchange } = useUserExchangeCurrency();

  const handleExchange = (): void => {
    exchange({
      userId: userId as number,
      bankId: +exchangeBank,
      currencyIn: fromCurrency,
      currencyOut: toCurrency,
      amount: +exchangeAmount,
    }, {
      onError: (error) => {
        if (typeof (error as AxiosError).response?.data !== "string") {
          setError('Проблемы на сервере')
        } else {
          setError((error as AxiosError).response?.data)
        }
      }
    });
  }

  const { data: banks = [] } = useBank();
  const { data: banksRates = [] } = useBankExchange();

  const { data: bankReviews = [] } = useBankReviewFind({
    bankId: exchangeBank,
  });

  return (
    <>
      <Chip label={`$${usdAmount}`} onClick={handleOpen} />
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <>
          <Box sx={style}>
            <Typography variant="h5" sx={{ textAlign: "center" }}>
              Счета
            </Typography>
            {bankAccount.map((account) => (
              <Typography
                key={account.id}
                sx={{ fontSize: 14 }}
                color="text.secondary"
                gutterBottom
              >
                {account.currency}: {account.amount}
              </Typography>
            ))}
            <Box sx={{ display: "flex", mt: 2, "&>*": { mr: 1 } }}>
              <CreateBankAccount />
              <CreateBankReview bankId={exchangeBank} />
            </Box>
            <Box sx={{ mt: 2 }}>
              <FormControl sx={{ mt: 1, mb: 1 }} fullWidth>
                <InputLabel id="demo-simple-select-label">Банк</InputLabel>
                <Select
                  labelId="demo-simple-select-label"
                  id="demo-simple-select"
                  label="Банк"
                  value={exchangeBank}
                  onChange={(e) => setExchangeBank(+e.target.value)}
                >
                  {banks.map(({ id, name, description, country }) => (
                    <MenuItem key={id} value={id}>
                      <Box>
                        <Typography>
                          {name} ({country})
                        </Typography>
                        <Typography
                          sx={{ fontSize: 12 }}
                          color="text.secondary"
                        >
                          {description}
                        </Typography>

                        {banksRates
                          .slice()
                          .filter(({ bankId }) => bankId === id)
                          .map((rate) => (
                            <Typography
                              key={rate.id}
                              sx={{ fontSize: 14 }}
                              color="text.secondary"
                            >
                              {rate.currencyIn}/{rate.currencyOut} rate:{" "}
                              {rate.rate} fee: {rate.fee}
                            </Typography>
                          ))}
                      </Box>
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Box>
            <Box sx={{ display: "flex", mt: 1, mb: 2 }}>
              <FormControl sx={{ mr: 2 }} fullWidth>
                <InputLabel id="demo-simple-select-label">Из</InputLabel>
                <Select
                  labelId="demo-simple-select-label"
                  id="demo-simple-select"
                  label="Из"
                  value={fromCurrency}
                  onChange={(e) => setFromCurrency(e.target.value)}
                >
                  {bankAccount.map(({ currency }) => (
                    <MenuItem key={currency} value={currency}>
                      {currency}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
              <FormControl sx={{ mr: 2 }} fullWidth>
                <InputLabel id="demo-simple-select-label">В</InputLabel>
                <Select
                  labelId="demo-simple-select-label"
                  id="demo-simple-select"
                  label="В"
                  value={toCurrency}
                  onChange={(e) => setToCurrency(e.target.value)}
                  disabled={!fromCurrency}
                >
                  {bankAccount
                    .slice()
                    .filter(({ currency }) => currency !== fromCurrency)
                    .map(({ currency }) => (
                      <MenuItem key={currency} value={currency}>
                        {currency}
                      </MenuItem>
                    ))}
                </Select>
              </FormControl>
              <TextField
                type="number"
                fullWidth
                value={exchangeAmount}
                onChange={(e) => setExchangeAmount(e.target.value ? Math.abs(+e.target.value): '')}
                disabled={!fromCurrency || !toCurrency}
              />
            </Box>
            <Box sx={{ m: 1 }}>
              <Button
                fullWidth
                disabled={!fromCurrency || !toCurrency || !exchangeAmount}
                onClick={handleExchange}
              >
                Обменять
              </Button>
              <Typography sx={{ textAlign: "center", color: "#e50707" }}>{error && error}</Typography>
            </Box>
          </Box>
          {!!exchangeBank && !!bankReviews.length && (
            <Box sx={styleReviews}>
              <Typography variant="h5" sx={{ textAlign: "center", mb: 1 }}>
                Reviews
              </Typography>
              <Box sx={{ maxHeight: 300, overflow: "auto", p: 1, pr: 2 }}>
                {bankReviews.map((review) => (
                  <Box
                    key={review.id}
                    sx={{
                      mt: 1,
                      p: 1,
                      borderRadius: 1,
                      backgroundColor: "rgba(0,0,0,0.02)",
                    }}
                  >
                    <Typography
                        sx={{ fontSize: 10, mt: 0.5 }}
                        color="text.secondary"
                    >
                      {review.userId}
                    </Typography>
                    <Rating value={review.rate} readOnly />
                    <Typography>{review.review}</Typography>
                    {!!review.createdAt && (
                      <Typography
                        sx={{ fontSize: 10, mt: 0.5 }}
                        color="text.secondary"
                      >
                        {new Date(review.createdAt).getDate()}.
                        {new Date(review.createdAt).getMonth()}.
                        {new Date(review.createdAt).getFullYear()}
                      </Typography>
                    )}
                  </Box>
                ))}
              </Box>
            </Box>
          )}
        </>
      </Modal>
    </>
  );
};

export default BankAccount;

const style = {
  position: "absolute" as "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  borderRadius: 4,
  boxShadow: 24,
  p: 4,
};

const styleReviews = {
  position: "absolute" as "absolute",
  top: "50%",
  right: "50%",
  transform: "translate(200%, -50%)",
  width: 200,
  bgcolor: "background.paper",
  borderRadius: 4,
  boxShadow: 24,
  p: 4,
};
