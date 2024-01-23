const { expect } = require("chai")
const { ethers } = require("hardhat")

describe("Payments", function () {
    let acc1
    let acc2
    let payments
    let contractAddress

    beforeEach(async function() {
        [acc1, acc2] = await ethers.getSigners()
        const Payments = await ethers.getContractFactory("Payments", acc1)
        payments = await ethers.deployContract("Payments")
        contractAddress = await payments.getAddress()
    })

    it("should be deployed", async function() {
        expect(contractAddress).to.be.properAddress
    })

    it("should have 0 ether by default", async function() {
        const balance = await payments.currentBalance()
        expect(balance).to.eq(0)
    })

    it("should be possible to send funds", async function() {
        const sum = 100
        const msg = "Hello from hardhat"
        const transaction = await payments.connect(acc2).pay(msg, { value: sum })

        await expect(() => transaction)
            .to.changeEtherBalances([acc2, payments], [-sum, sum])

        await transaction.wait()

        const newPayment = await payments.getPayment(acc2.address, 0)
        expect(newPayment.message).to.eq(msg)
        expect(newPayment.amount).to.eq(sum)
        expect(newPayment.from).to.eq(acc2.address)
    })
})