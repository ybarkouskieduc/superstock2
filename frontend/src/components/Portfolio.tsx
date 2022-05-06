import {
    Box,
    Card,
    CardContent,
    Typography,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    Button,
} from "@mui/material"
import { useState } from "react";

import { useStock, useStockAccountFind, useStockPriceProfit } from "../hooks/queries";
import { useUserId } from "../hooks/lib";


export const PortfolioList: React.FC = () => {
    const [profitPeriod, setProfitPeriod] = useState<number | undefined>();

    const [userId] = useUserId();
    const { data: stocks = [] } = useStock();
    const { data: accountStocks = [] } = useStockAccountFind({
        userId: userId as number,
    });

    const calculateTotalNetWorth = (): number => {
        let totalNetWorth = 0;
        accountStocks.forEach((accountStock) => {
            const currentStock = stocks.find(({ id }) => id === accountStock.stockId);
            if (!currentStock) return;
            totalNetWorth += (currentStock.currentStockPrice || 0) * (accountStock.amount || 0);
        });
        return totalNetWorth;
    }

    if (!accountStocks.length) {
        return (
            <Box
                sx={{
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                    width: "100%",
                    mt: "100px",
                    mb: "100px"
                }}
            >
                <Typography variant="h5" component="div">
                    У вас пока нет акций
                </Typography>
            </Box>
        )
    }
    return (
        <Box sx={{ width: "50%", textAlign: "left", mt: "24px", mb: "24px" }}>
            <Box
                sx={{ display: "flex", alignItems: "center", justifyContent: "space-between" }}
            >
                <Typography sx={{ m: 1, ml: 4 }}>Портфель</Typography>
                <Box>
                    <Button
                        sx={{ m: 1 }}
                        onClick={() => setProfitPeriod(1)}
                        variant={profitPeriod === 1 ? "contained" : "outlined"}
                    >
                        ЧАС
                    </Button>
                    <Button
                        sx={{ m: 1 }}
                        onClick={() => setProfitPeriod(24)}
                        variant={profitPeriod === 24 ? "contained" : "outlined"}
                    >
                        ДЕНЬ
                    </Button>
                    <Button
                        sx={{ m: 1 }}
                        onClick={() => setProfitPeriod(24 * 7)}
                        variant={profitPeriod === 24 * 7 ? "contained" : "outlined"}
                    >
                        НЕДЕЛЯ
                    </Button>
                    <Button
                        sx={{ m: 1 }}
                        onClick={() => setProfitPeriod(undefined)}
                        variant={profitPeriod === undefined ? "contained" : "outlined"}
                    >
                        ВСЕ ВРЕМЯ
                    </Button>
                </Box>
            </Box>
            <Card sx={{ m: 1, width: "100%" }}>
                <CardContent>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>
                                    <Typography sx={{ fontSize: "12px" }}>
                                        НАИМЕНОВАНИЕ
                                    </Typography>
                                </TableCell>
                                <TableCell>
                                    <Typography sx={{ fontSize: "12px" }}>
                                        КОЛИЧЕСТВО
                                    </Typography>
                                </TableCell>
                                <TableCell>
                                    <Typography sx={{ fontSize: "12px" }}>
                                        ПРИБЫЛЬ/УБЫТКИ
                                    </Typography>
                                </TableCell>
                                <TableCell>
                                    <Typography sx={{ fontSize: "12px" }}>
                                        ТЕКУЩАЯ СТОИМОСТЬ
                                    </Typography>
                                </TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {accountStocks.map(({ stockId, amount }) => (
                                <PortfolioListItem
                                    key={stockId}
                                    stockId={stockId}
                                    amount={amount}
                                    stocks={stocks}
                                    userId={userId}
                                    profitTime={profitPeriod}
                                />
                            ))}
                        </TableBody>
                    </Table>
                    <Box style={{
                        display: "flex",
                        justifyContent: "space-between",
                        marginTop: "20px"
                    }}>
                        <Typography>
                            Oбщая стоимость:
                        </Typography>
                        <Typography sx={{ fontWeight: "600" }}>
                            {calculateTotalNetWorth()} <Typography component={'span'} sx={{ fontSize: "10px", display: "inline-block" }}>USD$</Typography>
                        </Typography>
                    </Box>
                </CardContent>
            </Card>
        </Box>
    )
}

const PortfolioListItem: React.FC<{
    stockId?: number,
    amount?: number,
    stocks: any[],
    userId?: number,
    profitTime?: number | undefined
}> = ({ stockId, amount, stocks, userId, profitTime }) => {
    const stock = stocks.find(({ id }) => id === stockId);
    const { data: profit } = useStockPriceProfit({ stockId, userId, dateFrom: profitTime });
    return stock && (
        <TableRow>
            <TableCell>
                <Typography variant="h5">
                    {stock.sign}
                </Typography>
            </TableCell>
            <TableCell>
                <Typography>
                    {amount}
                </Typography>
            </TableCell>
            <TableCell>
                <Typography>
                    {profit} <Typography component={'span'} sx={{ fontSize: "10px", display: "inline-block" }}>USD$</Typography>
                </Typography>
            </TableCell>
            <TableCell>
                <Typography>
                    {(stock.currentStockPrice || 0) * (amount || 0)} <Typography component={'span'} sx={{ fontSize: "10px", display: "inline-block" }}>USD$</Typography>
                </Typography>
            </TableCell>
        </TableRow>
    )
}