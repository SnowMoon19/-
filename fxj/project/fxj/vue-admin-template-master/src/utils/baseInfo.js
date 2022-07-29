/**
 * 计算年龄
 */
export function calAge(birth) {
  const birth1 = birth ? new Date(birth) : new Date()
  const day = birth1.getDate()
  const month = birth1.getMonth() + 1// 注意月份需要+1
  const year = birth1.getFullYear()
  const d = new Date() // 今天
  console.log(d)
  const day2 = d.getDate()
  const month2 = d.getMonth() + 1// 注意月份需要+1
  const year2 = d.getFullYear()
  let ans = year2 - year
  if (month2 === month && day2 < day) ans -= 1
  if (month2 < month) ans -= 1
  return Math.max(0, ans)
}

/**
 * 计算星座
 */
export function calSign(birth) {
  const birth1 = birth ? new Date(birth) : new Date()
  const day = birth1.getDate()
  const month = birth1.getMonth() + 1// 注意月份需要+1
  const s = '魔羯水瓶双鱼牡羊金牛双子巨蟹狮子处女天秤天蝎射手魔羯';
  const arr2 = [20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22];
  const result2 = s.substr(month * 2 - (day < arr2[month - 1] ? 2 : 0), 2)
  return result2
}
