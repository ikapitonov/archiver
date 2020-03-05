/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   ft_strsplit.c                                      :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: matruman <marvin@42.fr>                    +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2019/09/17 15:25:43 by matruman          #+#    #+#             */
/*   Updated: 2019/09/20 20:00:05 by matruman         ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

#include <stdlib.h>
#include "libft.h"

static int		ft_strsplit_fn(char **arr, char const *s, char c, int i)
{
	int		k;
	int		count;

	count = 0;
	while (s[i])
	{
		if (s[i] != c)
		{
			k = i;
			while (s[i] != c && s[i])
				i++;
			arr[count] = (char *)malloc(i - k + 1);
			if (arr[count])
			{
				ft_strncpy(arr[count], &s[k], (size_t)(i - k));
				arr[count][i - k] = 0;
			}
			else
				return (1);
			count++;
		}
		if (s[i])
			i++;
	}
	return (0);
}

static int		ft_count(char const *s, char c)
{
	int		i;
	int		count;

	i = 0;
	count = 0;
	while (s[i])
	{
		if (s[i] != c)
			count++;
		while (s[i] != c && s[i])
			i++;
		if (s[i])
			i++;
	}
	return (count);
}

static void		ft_free_arr(char ***aarr)
{
	char	**arr;
	int		i;

	arr = *aarr;
	i = 0;
	while (arr[i])
	{
		free(arr[i]);
		i++;
	}
	free(*aarr);
}

char			**ft_strsplit(char const *s, char c)
{
	int		count;
	char	**arr;

	if (s)
	{
		count = ft_count(s, c);
		arr = (char **)malloc((count + 1) * sizeof(char *));
		if (arr)
		{
			if (ft_strsplit_fn(arr, s, c, 0))
			{
				ft_free_arr(&arr);
				return (NULL);
			}
			arr[count] = NULL;
		}
	}
	return (s ? arr : (char **)NULL);
}
